package com.qiyue.mq.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class RabbitMQMessageConvert implements MessageConverter {


    private GenericMessageConverter delegate;

    public RabbitMQMessageConvert(GenericMessageConverter rabbitMQMessageConvert) {
        Preconditions.checkNotNull(rabbitMQMessageConvert);
        this.delegate = rabbitMQMessageConvert;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        //messageProperties.setExpiration(String.valueOf(2 * 60));//设置过期时间
        com.qiyue.mq.rabbit.api.Message message = (com.qiyue.mq.rabbit.api.Message) object;
        messageProperties.setDelay(message.getDelayMills());
        return this.delegate.toMessage(object, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.delegate.fromMessage(message);
    }
}
