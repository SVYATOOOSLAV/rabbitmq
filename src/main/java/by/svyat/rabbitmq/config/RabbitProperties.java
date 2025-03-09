package by.svyat.rabbitmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class RabbitProperties {

    private String firstQueueName;
    private String secondQueueName;
    private String hostName;
    private Integer port;
    private String username;
    private String password;
    private String virtualHost;
}
