package com.qiyue.mq.common.serializer;

import com.qiyue.mq.rabbit.api.Message;

public class JacksonSerializerFactory implements SerializerFactory {

    public static final JacksonSerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
