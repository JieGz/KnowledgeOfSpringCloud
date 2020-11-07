package com.qiyue.mq.core.producer.broker;

import com.google.common.base.Preconditions;
import com.qiyue.mq.rabbit.api.Message;
import com.qiyue.mq.rabbit.api.MessageProducer;
import com.qiyue.mq.rabbit.api.MessageType;
import com.qiyue.mq.rabbit.api.SendCallback;
import com.qiyue.mq.rabbit.api.exception.MessageRunTimeException;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class RabbitMQProducerClient implements MessageProducer {

    @Resource
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws MessageRunTimeException {
        System.out.println("RabbitMQProducerClient#send:"+message);
        Preconditions.checkNotNull(message);
        switch (message.getMessageType()) {
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIABILITY:
                rabbitBroker.reliabilitySend(message);
                break;
            default:
                break;
        }

    }

    @Override
    public void send(Message message, SendCallback callback) throws MessageRunTimeException {

    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {
        messages.forEach(message -> {
            message.setMessageType(MessageType.RAPID);
            MessageHolder.add(message);
        });
        rabbitBroker.batchSend();
    }
}
