package by.svyat.rabbitmq.controller;

import by.svyat.rabbitmq.service.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RabbitController {
    private final MessageSender messageSender;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody String message) {
        messageSender.sendMessage(message);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
