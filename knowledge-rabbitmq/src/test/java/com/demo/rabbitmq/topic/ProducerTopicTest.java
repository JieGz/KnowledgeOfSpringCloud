package com.demo.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class ProducerTopicTest {

    private final String EXCHANGE_TOPIC = "exchange_topic";

    @Test
    public void topicProduceSendMessage() {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("test");
        connectionFactory.setUsername("luke");
        connectionFactory.setPassword("luke");

        try (final Connection connection = connectionFactory.newConnection();
             final Channel channel = connection.createChannel()) {

            //1.通过channel声明一个交换机
            channel.exchangeDeclare(EXCHANGE_TOPIC, "topic");
            //2.通过channel将消息发送到交换机
            String routingKey = "user.name.a";
            String msg = "This is " + routingKey + " message";
            channel.basicPublish(EXCHANGE_TOPIC, routingKey, null, msg.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
