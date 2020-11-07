package com.knowledge.rabbitmq.delay.mq;

import com.knowledge.rabbitmq.delay.constants.DelayTypeEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.knowledge.rabbitmq.delay.config.RabbitMQConfig.*;

@Component
public class DelayMessageSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String msg, DelayTypeEnum type) {
        switch (type) {
            case DELAY_10s:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEA_ROUTING_KEY, msg);
                break;
            case DELAY_60s:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEB_ROUTING_KEY, msg);
                break;
        }
    }
}
