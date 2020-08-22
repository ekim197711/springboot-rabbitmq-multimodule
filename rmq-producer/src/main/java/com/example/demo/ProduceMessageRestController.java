package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    @GetMapping("/api/generate/{nMessages}")
    public String generate(@PathVariable Integer nMessages){
        for (int i = 0; i < nMessages; i++){
            message("This message is generated! " + i + ", time: " + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        }
        return "Done...";
    }
}
