package by.svyat.rabbitmq.service;

import by.svyat.rabbitmq.config.RabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static by.svyat.rabbitmq.config.RabbitConfig.MESSAGE_EXCHANGE;
import static by.svyat.rabbitmq.config.RabbitConfig.ROUTING_KEY;

@Service
@RequiredArgsConstructor
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(
                MESSAGE_EXCHANGE,
                ROUTING_KEY,
                message
        );
    }

}
