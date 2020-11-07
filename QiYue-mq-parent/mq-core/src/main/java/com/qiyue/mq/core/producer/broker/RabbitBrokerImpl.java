package com.qiyue.mq.core.producer.broker;

import com.qiyue.mq.core.producer.entities.BrokerMessage;
import com.qiyue.mq.core.producer.service.MessageStoreService;
import com.qiyue.mq.rabbit.api.Message;
import com.qiyue.mq.rabbit.api.MessageType;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RabbitBrokerImpl implements RabbitBroker {

    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    public void setRabbitTemplateContainer(RabbitTemplateContainer rabbitTemplateContainer) {
        this.rabbitTemplateContainer = rabbitTemplateContainer;
    }

    private MessageStoreService messageStoreService;

    @Autowired
    public void setMessageStoreService(MessageStoreService messageStoreService) {
        this.messageStoreService = messageStoreService;
    }

    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void reliabilitySend(Message message) {
        message.setMessageType(MessageType.RELIABILITY);

        final BrokerMessage bm = messageStoreService.selectByMessageId(message.getMessageId());
        if (bm == null) {
            //1.向本地消息表中插入一个消息
            BrokerMessage brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setMessage(message);
            brokerMessage.setCreateTime(LocalDateTime.now());
            brokerMessage.setUpdateTime(LocalDateTime.now());
            brokerMessage.setNextRetry(LocalDateTime.now().plusMinutes(5L));
            brokerMessage.setTryCount(0);
            brokerMessage.setStatus("0");
            messageStoreService.insert(brokerMessage);
        }
        //2.向mq中发送消息
        sendKernel(message);
    }


    @Override
    public void batchSend() {
        MessageHolder.clear().forEach(this::sendTask);
    }

    private void sendTask(Message message) {
        Runnable runnable = () -> {
            final String topic = message.getTopic();
            final String routingKey = message.getRoutingKey();

            final CorrelationData correlationData = new CorrelationData(String.format("%s#%s#%s", message.getMessageId(),
                    System.currentTimeMillis(), message.getMessageType()));

            //使用池化的RabbitTemplate
            final RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
        };

        AsyncBaseQueue.submit(runnable);
    }

    /**
     * 使用异步线程池,发送消息
     *
     * @param message 消息
     */
    private void sendKernel(Message message) {
        sendTask(message);
    }
}
