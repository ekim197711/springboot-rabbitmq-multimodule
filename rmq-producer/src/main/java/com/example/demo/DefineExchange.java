package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefineExchange {
    private final RabbitAdmin rabbitAdmin;


    @PostConstruct
    public void createExchange() {
        rabbitAdmin.declareExchange(ExchangeBuilder.
                directExchange("myexchange1").
                build());
    }

}
