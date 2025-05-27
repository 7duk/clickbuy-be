package dev.sideproject.ndx2.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sideproject.ndx2.dto.AccountDto;
import dev.sideproject.ndx2.service.MailService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitMQConsumer {
    final MailService mailService;
    final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue.name}",containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(Message message) throws MessagingException, JsonProcessingException {
        log.info("Consumer received message at {}.", LocalDateTime.now());
        String rawBody = new String(message.getBody(), StandardCharsets.UTF_8);
        processMessage(rawBody);
    }

    public void processMessage(String message) throws JsonProcessingException, MessagingException{
        AccountDto accountDto = objectMapper.readValue(message, AccountDto.class);
        mailService.sendHtmlVerificationMail(accountDto);
    }
}
