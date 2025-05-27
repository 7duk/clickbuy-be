package dev.sideproject.ndx2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
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

    @Value("${rabbitmq.queue.name}")
    String rabbitMqQueueName;

    @Value("${rabbitmq.username}")
    String rabbitMqUsername;

    @Value("${rabbitmq.password}")
    String rabbitMqPassword;

    @Value("${rabbitmq.exchange.name}")
    String rabbitMqExchangeName;

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
    public Queue myQueue() {
        return new Queue(rabbitMqQueueName);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(rabbitMqExchangeName);
    }

//    @Bean
//    public DirectMessageListenerContainer rabbitListenerContainer() {
//        DirectMessageListenerContainer container = new DirectMessageListenerContainer();
//        container.addQueueNames(rabbitMqQueueName);
//        container.setConnectionFactory(connectionFactory());
//        return container;
//    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(CachingConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

}