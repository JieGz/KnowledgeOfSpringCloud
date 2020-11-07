package com.qiyue.mq.common.convert;

import com.qiyue.mq.common.serializer.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class GenericMessageConverter implements MessageConverter {

    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(this.serializer.serializeRaw(object), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.serializer.deSerialize(message.getBody());
    }
}
