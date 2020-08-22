package com.example.demo.reject;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

public class RejectAndReadMessage implements ChannelCallback<Message> {
    final MessagePropertiesConverter propertiesConverter = new DefaultMessagePropertiesConverter();

    @Override
    public Message doInRabbit(Channel channel) throws Exception {
        GetResponse messageResponse = channel.basicGet("mike", false);
        if (messageResponse == null) {
            return null;
        }
        channel.basicReject(messageResponse.getEnvelope().getDeliveryTag(), true);
        return new Message(messageResponse.getBody(), propertiesConverter.toMessageProperties(
                messageResponse.getProps(), messageResponse.getEnvelope(), "UTF-8"));
    }
}
