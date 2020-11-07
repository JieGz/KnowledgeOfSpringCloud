package com.qiyue.mq.core.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.qiyue.mq.common.convert.GenericMessageConverter;
import com.qiyue.mq.common.convert.RabbitMQMessageConvert;
import com.qiyue.mq.common.serializer.JacksonSerializerFactory;
import com.qiyue.mq.core.producer.service.MessageStoreService;
import com.qiyue.mq.rabbit.api.Message;
import com.qiyue.mq.rabbit.api.MessageType;
import com.qiyue.mq.rabbit.api.exception.MessageRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {
    /**
     * key: topic,exchange
     * value: rabbit template
     */
    private final Map<String, RabbitTemplate> templateMap = new ConcurrentHashMap<>();

    private final Splitter splitter = Splitter.on("#");

    private ConnectionFactory connectionFactory;

    @Autowired
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private MessageStoreService messageStoreService;

    @Autowired
    public void setMessageStoreService(MessageStoreService messageStoreService) {
        this.messageStoreService = messageStoreService;
    }

    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        final String topic = message.getTopic();
        final RabbitTemplate template = templateMap.get(topic);

        if (template != null) {
            return template;
        }

        log.info("创建一个template");

        final RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);
        newTemplate.setRoutingKey(message.getRoutingKey());
        newTemplate.setRetryTemplate(new RetryTemplate());

        //添加自定义的序列化和反序列化
        newTemplate.setMessageConverter(new RabbitMQMessageConvert(new GenericMessageConverter(JacksonSerializerFactory.INSTANCE.create())));

        final String messageType = message.getMessageType();
        if (!MessageType.RAPID.equals(messageType)) {
            newTemplate.setConfirmCallback(this);
        }

        templateMap.put(topic, newTemplate);

        return newTemplate;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData == null) return;
        final String correlationDataId = correlationData.getId();
        if (Strings.isNullOrEmpty(correlationDataId)) return;
        final List<String> list = splitter.splitToList(correlationDataId);
        String messageId = list.get(0);
        Long sendTime = Long.parseLong(list.get(1));
        String messageType = list.get(2);
        if (ack) {
            if (messageType.equals(MessageType.RELIABILITY)) {
                messageStoreService.succeed(messageId);
            }
            log.info("send message is OK,confirm messageId:{},sendTime:{}", messageId, sendTime);
        } else {
            log.error("send message is Fail,confirm messageId:{},sendTime:{}", messageId, sendTime);
        }
    }
}
