package com.qiyue.mq.rabbit.api;

/**
 * 消费者消费消息
 */
public interface MessageListener {

    void onMessage(Message message);
}
