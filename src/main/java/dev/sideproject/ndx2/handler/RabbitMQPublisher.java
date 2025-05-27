package dev.sideproject.ndx2.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sideproject.ndx2.dto.AccountDto;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitMQPublisher {
    final ObjectMapper objectMapper;
    final RabbitTemplate rabbitTemplate;
    static final int MAX_RETRIES = 3;
    static final long RETRY_DELAY_MS = 3000;

    final Map<String, MessageData<AccountDto>> retryData = new ConcurrentHashMap<>();
    final Map<String, Integer> retryCountMap = new ConcurrentHashMap<>();
    final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    public RabbitMQPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        rabbitTemplate.setConfirmCallback(
                (CorrelationData correlationData, boolean ack, String cause) -> {
                    String id = correlationData != null ? correlationData.getId() : "unknown";
                    if (ack) {
                        log.info("Publisher sent message successfully at : {}", LocalDateTime.now());
                        retryCountMap.remove(id);
                        retryData.remove(id);
                    } else {
                        log.error("Publisher sent message failed at : {} because {}", LocalDateTime.now(), cause);
                        retry(id);
                    }
                }
        );
        rabbitTemplate.setReturnsCallback((returnedMessage) -> {
            log.warn("Publisher returned message because: {}", returnedMessage.getReplyText());
        });
    }

    public void sendMessage(String messageId, AccountDto accountDto) throws JsonProcessingException {
        String msgConverted = objectMapper.writeValueAsString(accountDto);
        Message msg = new Message(msgConverted.getBytes(StandardCharsets.UTF_8));
        CorrelationData correlationData = new CorrelationData(messageId);
        retryData.put(messageId, new MessageData<AccountDto>(messageId, accountDto, rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey()));
        rabbitTemplate.send(rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey(), msg, correlationData);
    }

    public void retry(String messageId) {
        int retryCount = retryCountMap.getOrDefault(messageId, 0);
        if (retryCount < MAX_RETRIES) {
            retryCountMap.put(messageId, retryCount + 1);
            log.warn("Publisher retry attempt {} for message {}", retryCount + 1, messageId);
            MessageData messageData = retryData.get(messageId);
            scheduler.schedule(() -> {
                try {
                    sendMessage(messageData.getCorrelationDataId(), (AccountDto) messageData.getData());
                } catch (JsonProcessingException e) {
                    log.warn("Publisher retry processing failed because: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            }, RETRY_DELAY_MS, TimeUnit.MILLISECONDS);
        } else {
            log.error("Publisher retry exhausted for message {}", messageId);
            retryCountMap.remove(messageId);
        }
    }


}
