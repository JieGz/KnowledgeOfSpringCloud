package com.demo.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ConsumerRoutingGreenTest {
    private final String EXCHANGE_NAME = "exchange_direct";

    @Test
    public void routingConsumerMessage() {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("test");
        connectionFactory.setUsername("luke");
        connectionFactory.setPassword("luke");

        try (final Connection connection = connectionFactory.newConnection();
             final Channel channel = connection.createChannel()) {

            //1.通过channel声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            //2.通过channel声明一个消息队列
            final String queue = channel.queueDeclare().getQueue();
            //3.通过channel将exchange和queue绑定
            channel.queueBind(queue, EXCHANGE_NAME, "green");
            //4.通过channel开始消费消息
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("消费者2：" + new String(delivery.getBody(), StandardCharsets.UTF_8));
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
