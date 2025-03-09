package by.svyat.rabbitmq;

import by.svyat.rabbitmq.service.RabbitReceiver;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import static by.svyat.rabbitmq.config.RabbitConfig.MESSAGE_EXCHANGE;
import static by.svyat.rabbitmq.config.RabbitConfig.ROUTING_KEY;
import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

public class RabbitReceiverTest extends RabbitInitializer {

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @BeforeEach
    void setUp() {
        // Настраиваем логгер
        Logger logger = (Logger) LoggerFactory.getLogger(RabbitReceiver.class);
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void testReceiveFirstQueue() {

        // Отправляем сообщение
        String testMessage = "Test Message First Queue";
        rabbitTemplate.convertAndSend(
                MESSAGE_EXCHANGE,
                ROUTING_KEY,
                testMessage
        );

        // Ждём, пока лог появится
        await().atMost(Duration.ofSeconds(5)).until(() ->
                listAppender.list.stream()
                        .anyMatch(event -> event.getFormattedMessage()
                                .contains("Received message from first queue: " + testMessage)
                        )
        );

        // Проверяем, что сообщение действительно зафиксировано в логах
        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains("Received message from first queue: " + testMessage);
    }

    @Test
    void testReceiveSecondQueue() {
        // Отправляем сообщение
        String testMessage = "Test Message Second Queue";
        rabbitTemplate.convertAndSend(
                MESSAGE_EXCHANGE,
                ROUTING_KEY,
                testMessage
        );

        // Ждём, пока лог появится
        await().atMost(Duration.ofSeconds(5)).until(() ->
                listAppender.list.stream()
                        .anyMatch(event -> event.getFormattedMessage()
                                .contains("Received message from second queue: " + testMessage)
                        )
        );

        // Проверяем, что сообщение действительно зафиксировано в логах
        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains("Received message from second queue: " + testMessage);
    }
}
