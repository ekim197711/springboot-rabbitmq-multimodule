package com.example.demo.reject;

import com.example.demo.reject.RejectAndReadMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PeekRestController {
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/api/peek/{nMessages}")
    public String peek(@PathVariable Integer nMessages){
        String result ="Message from the queue: \n";
        for (int i = 0; i < nMessages; i++){
            Message message = rabbitTemplate.execute(new RejectAndReadMessage());
            if (message == null)
                break;
            String body = new String(message.getBody());
            result+=body + "\n";
            log.info("Messagebody peek: {}", body);
        }
        return result;
    }


}
