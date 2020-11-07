package com.qiyue.mq.rabbit.api;

import com.google.common.base.Strings;
import com.qiyue.mq.rabbit.api.exception.MessageRunTimeException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageBuilder {

    /** 消息的唯一id */
    private String messageId;

    /** 消息的主题 */
    private String topic;

    /** 消息的路由规则 */
    private String routingKey = "";

    /** 消息的附加属性 */
    private Map<String, Object> attributes = new HashMap<>();

    /** 延迟消息的参数配置 */
    private int delayMills;

    /** 消息类型:默认是一个确认消息 */
    private String messageType = MessageType.CONFIRM;


    private MessageBuilder() {

    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public MessageBuilder messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder topic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder routingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder attributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder attributes(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    public MessageBuilder delayMills(int delayMills) {
        this.delayMills = delayMills;
        return this;
    }

    public MessageBuilder messageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public Message build() {
        if (Strings.isNullOrEmpty(messageId)) {
            this.messageId = UUID.randomUUID().toString().trim().replaceAll("-", "");
        }

        if (Strings.isNullOrEmpty(topic)) {
            throw new MessageRunTimeException("topic is null");
        }

        return new Message(messageId, topic, routingKey, attributes, delayMills, messageType);
    }
}
