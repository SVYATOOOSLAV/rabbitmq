package by.svyat.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class RabbitInitializer {

    private static final String EXCHANGE_NAME = "message_exchange";
    private static final String QUEUE_NAME_1 = "FirstQueue";
    private static final String QUEUE_NAME_2 = "SecondQueue";
    private static final String ROUTING_KEY = "message_routing_key";

    @Container
    static final RabbitMQContainer rabbitmq =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:management"))
                    .withExchange(EXCHANGE_NAME, "direct") // Создаём Exchange
                    .withQueue(QUEUE_NAME_1) // Создаём очередь FirstQueue
                    .withQueue(QUEUE_NAME_2) // Создаём очередь SecondQueue
                    .withBinding(EXCHANGE_NAME, QUEUE_NAME_1, Map.of(), ROUTING_KEY, "queue") // Привязка FirstQueue
                    .withBinding(EXCHANGE_NAME, QUEUE_NAME_2, Map.of(), ROUTING_KEY, "queue"); // Привязка SecondQueue

    @DynamicPropertySource
    static void overridePropertiesInternal(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitmq::getHost);
        registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitmq::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitmq::getAdminPassword);
    }

    @Autowired
    public RabbitTemplate rabbitTemplate;
}
