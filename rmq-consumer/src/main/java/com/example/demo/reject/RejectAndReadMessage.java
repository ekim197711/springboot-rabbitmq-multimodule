package com.example.demo.reject;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

import java.util.ArrayList;
import java.util.List;

public class RejectAndReadMessage implements ChannelCallback<List<Message>> {
    private final int nMessages;
    final MessagePropertiesConverter propertiesConverter = new DefaultMessagePropertiesConverter();

    public RejectAndReadMessage(int nMessages) {
        this.nMessages = nMessages;
    }

    @Override
    public List<Message> doInRabbit(Channel channel) throws Exception {
        List<GetResponse> messages = new ArrayList<>();
        List<Message> result = new ArrayList<>();
        for (int i = 0; i < nMessages; i++){
            GetResponse messageResponse = channel.basicGet("mike", false);
            if (messageResponse == null) {
                break;
            }
            messages.add(messageResponse);
        }
        for (GetResponse messageResponse : messages) {
            channel.basicReject(messageResponse.getEnvelope().getDeliveryTag(), true);
            result.add(new Message(messageResponse.getBody(), propertiesConverter.toMessageProperties(
                    messageResponse.getProps(), messageResponse.getEnvelope(), "UTF-8")));
        }
        return result;
    }
}
