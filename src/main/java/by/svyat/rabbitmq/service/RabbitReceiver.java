package by.svyat.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitReceiver {

    @RabbitListener(
            queues = "${spring.rabbitmq.first-queue-name}"
    )
    public void receiveFirstQueue(String message) {
        log.info("Received message from first queue: {}", message);
    }

    @RabbitListener(
            queues = "${spring.rabbitmq.second-queue-name}"
    )
    public void receiveSecondQueue(String message) {
        log.info("Received message from second queue: {}", message);
    }
}