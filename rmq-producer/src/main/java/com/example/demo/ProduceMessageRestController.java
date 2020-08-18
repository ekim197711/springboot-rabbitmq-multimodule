package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class ProduceMessageRestController {

    private final RabbitTemplate rabbitTemplate;
    @PostMapping("/api/message")
    public String message(@RequestBody String message){
        rabbitTemplate.send("myexchange1","", new Message(message.getBytes(StandardCharsets.UTF_8),
                new MessageProperties()));
        return "message sent...";
    }
}
