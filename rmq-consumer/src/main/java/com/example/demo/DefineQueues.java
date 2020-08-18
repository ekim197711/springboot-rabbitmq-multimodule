package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefineQueues {
    private final RabbitAdmin rabbitAdmin;


    @PostConstruct
    public void createQueue(){
        rabbitAdmin.declareQueue(new Queue("MyQ1"));
        rabbitAdmin.declareBinding(new Binding(
                "MyQ1", Binding.DestinationType.QUEUE,
"myexchange1", "", new HashMap<>()
        ));
    }

    @RabbitListener(queues={"MyQ1"})
    public void listen(Message message){
        log.info("MEssage {}", new String(message.getBody()));
    }
}
