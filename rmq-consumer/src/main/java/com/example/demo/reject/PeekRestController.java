package com.example.demo.reject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PeekRestController {
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/api/peek/{nMessages}")
    public String peek(@PathVariable Integer nMessages) {
        String result = "Message from the queue: \n";

        List<Message> messages = rabbitTemplate.execute(new RejectAndReadMessage(nMessages));
        for (Message message1 : messages) {
            String body = new String(message1.getBody());
            result += body + "\n";
            log.info("Messagebody peek: {}", body);
        }

        return result;
    }


}
