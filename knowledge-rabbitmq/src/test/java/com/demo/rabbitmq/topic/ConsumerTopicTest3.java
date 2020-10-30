package com.demo.rabbitmq.topic;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ConsumerTopicTest3 {
    private final String EXCHANGE_TOPIC = "exchange_topic";

    @Test
    public void topicConsumer() {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("test");
        connectionFactory.setUsername("luke");
        connectionFactory.setPassword("luke");

        try (final Connection connection = connectionFactory.newConnection();) {
            final Channel channel = connection.createChannel();
            //1.通过channel声明一个topic类型的交换机
            channel.exchangeDeclare(EXCHANGE_TOPIC, "topic");
            //2.通过channel声明一个消息队列
            final String queue = channel.queueDeclare().getQueue();
            //3.通过channel将交换机和消息队列绑定在一起
            channel.queueBind(queue, EXCHANGE_TOPIC, "*.*.user.#");
            //4.通过channel消费消息
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("消息者1：监听：*.*.user.#类型的消息：" + new String(delivery.getBody(), StandardCharsets.UTF_8));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
            });
            TimeUnit.MINUTES.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
