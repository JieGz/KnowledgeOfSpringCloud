package com.qiyue.mq.rabbit.api;

import com.qiyue.mq.rabbit.api.exception.MessageRunTimeException;

import java.util.List;

public interface MessageProducer {

    /**
     * 发送消息
     *
     * @param message 自定义的消息
     * @throws MessageRunTimeException
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * 发送消息,带回调方法
     *
     * @param message  自定消息
     * @param callback 回调
     * @throws MessageRunTimeException
     */
    void send(Message message, SendCallback callback) throws MessageRunTimeException;

    /**
     * 批量发送消息
     *
     * @param messages 批量的消息
     * @throws MessageRunTimeException
     */
    void send(List<Message> messages) throws MessageRunTimeException;
}
