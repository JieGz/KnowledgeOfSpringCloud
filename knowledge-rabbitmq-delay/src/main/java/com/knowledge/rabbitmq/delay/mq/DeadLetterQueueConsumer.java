package com.knowledge.rabbitmq.delay.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.knowledge.rabbitmq.delay.config.RabbitMQConfig.DEAD_LETTER_QUEUEA_NAME;
import static com.knowledge.rabbitmq.delay.config.RabbitMQConfig.DEAD_LETTER_QUEUEB_NAME;

@Slf4j
@Component
public class DeadLetterQueueConsumer {


    @RabbitListener(queues = DEAD_LETTER_QUEUEA_NAME)
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列A收到消息：{}", LocalDateTime.now(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    @RabbitListener(queues = DEAD_LETTER_QUEUEB_NAME)
    public void receiveB(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列B收到消息：{}", LocalDateTime.now(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
