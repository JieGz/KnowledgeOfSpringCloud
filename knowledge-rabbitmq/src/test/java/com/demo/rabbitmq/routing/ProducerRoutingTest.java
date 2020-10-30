package com.demo.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class ProducerRoutingTest {

    private final String EXCHANGE_NAME = "exchange_direct";

    @Test
    public void routingProducerSendMessage() {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("test");
        connectionFactory.setUsername("luke");
        connectionFactory.setPassword("luke");


        try (final Connection connection = connectionFactory.newConnection();
             final Channel channel = connection.createChannel()) {

            //通过channel声明一个direct模式的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String routingKey = "green";
            //通过路由key的方式将消息发送到交换机
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, ("This is " + routingKey + " color")
                    .getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
