package by.svyat.rabbitmq.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
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
}
