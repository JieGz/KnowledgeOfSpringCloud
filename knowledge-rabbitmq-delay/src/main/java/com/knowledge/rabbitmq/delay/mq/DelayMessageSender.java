package com.knowledge.rabbitmq.delay.mq;

import com.knowledge.rabbitmq.delay.constants.DelayTypeEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.knowledge.rabbitmq.delay.config.DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME;
import static com.knowledge.rabbitmq.delay.config.DelayedRabbitMQConfig.DELAYED_ROUTING_KEY;
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


    public void sendMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGEC_NAME, DELAY_QUEUEC_ROUTING_KEY, msg, a -> {
            //给消息设置过期时间
            a.getMessageProperties().setExpiration(String.valueOf(delayTime));
            return a;
        });
    }

    public void sendDelayMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, msg, a -> {
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }
}
