package com.qiyue.mq.common.serializer;

public interface Serializer {

    byte[] serializeRaw(Object data);

    String serialize(Object data);

    <T> T deSerialize(byte[] data);

    <T> T deSerialize(String data);
}
