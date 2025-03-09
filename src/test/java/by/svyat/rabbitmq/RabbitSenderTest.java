package by.svyat.rabbitmq;


import by.svyat.rabbitmq.service.MessageSender;
import by.svyat.rabbitmq.service.RabbitReceiver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class RabbitSenderTest extends RabbitInitializer {

    @Autowired
    private MessageSender messageSender;

    @MockBean
    private RabbitReceiver rabbitReceiver;

    @Test
    void testSendMessage() {
        String testMessage = "Hello RabbitMQ!";

        // Отправляем сообщение
        messageSender.sendMessage(testMessage);

        // Получаем сообщение из очереди
        String receivedMessageFromFirstQueue = (String) rabbitTemplate.receiveAndConvert("FirstQueue", 5000);
        String receivedMessageFromSecondQueue = (String) rabbitTemplate.receiveAndConvert("SecondQueue", 5000);

        // Проверяем, что сообщение дошло
        assertThat(receivedMessageFromFirstQueue)
                .isNotNull()
                .isEqualTo(testMessage);
        assertThat(receivedMessageFromSecondQueue)
                .isNotNull()
                .isEqualTo(testMessage);
    }
}
