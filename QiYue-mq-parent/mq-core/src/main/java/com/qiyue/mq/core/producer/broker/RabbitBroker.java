package com.qiyue.mq.core.producer.broker;

import com.qiyue.mq.rabbit.api.Message;

public interface RabbitBroker {

    void rapidSend(Message message);

    void confirmSend(Message message);

    void reliabilitySend(Message message);


    /**
     * 批量发送消息
     */
    void batchSend();
}
