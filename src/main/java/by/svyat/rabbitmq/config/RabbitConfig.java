package by.svyat.rabbitmq.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    public static final String MESSAGE_EXCHANGE = "message_exchange";
    public static final String ROUTING_KEY = "message_routing_key";

    private final RabbitProperties rabbitProperties;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setHost(rabbitProperties.getHostName());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
        connectionFactory.setPort(rabbitProperties.getPort());

        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.declareQueue(firstQueue());
        rabbitAdmin.declareQueue(secondQueue());
        return rabbitAdmin;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MESSAGE_EXCHANGE);
    }

    @Bean
    Binding firstQueueBinding(Queue firstQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(firstQueue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    Binding secondQueueBinding(Queue secondQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(secondQueue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public Queue firstQueue() {
        return new Queue(rabbitProperties.getFirstQueueName(), false);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(rabbitProperties.getSecondQueueName(), false);
    }
}
