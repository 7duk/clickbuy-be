package dev.sideproject.ndx2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfiguration {
    @Value("${rabbitmq.host}")
    String rabbitMqHost;

    @Value("${rabbitmq.username}")
    String rabbitMqUsername;

    @Value("${rabbitmq.password}")
    String rabbitMqPassword;

    @Value("${rabbitmq.exchange.name}")
    String rabbitMqExchangeName;

    @Value("${rabbitmq.queue.verify_queue}")
    String mailVerifyQueueName;

    @Value("${rabbitmq.verify_rtkey}")
    String mailVerifyRoutingKey;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(rabbitMqHost);
        factory.setUsername(rabbitMqUsername);
        factory.setPassword(rabbitMqPassword);
        return factory;
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue mailVerifyQueue() {
        return new Queue(mailVerifyQueueName);
    }

    @Bean
    public Binding emailBinding(Queue mailVerifyQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(mailVerifyQueue).to(directExchange).with(mailVerifyRoutingKey);
    }
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitMqExchangeName);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(CachingConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setDefaultRequeueRejected(true);
        return factory;
    }

}