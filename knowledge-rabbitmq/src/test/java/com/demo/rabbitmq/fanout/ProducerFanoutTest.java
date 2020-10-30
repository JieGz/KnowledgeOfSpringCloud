package com.demo.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * fanout模式：扇出 == 广播模式
 */
public class ProducerFanoutTest {

    private String exchange = "exchange-luke";


    @Test
    public void fanoutProducerSendMessage() {

        try {
            final ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.56.100");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("test");
            connectionFactory.setUsername("luke");
            connectionFactory.setPassword("luke");

            final Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();

            //1. 将通道声明指定交换机
            //参数1：交换机的名称
            //参数2：交换机的类型,fanout为广播类型
            channel.exchangeDeclare(exchange, "fanout");

            String msg = "fanout mode msg";

            //2.发布消息，发布到交换机上
            channel.basicPublish(exchange, "", null, msg.getBytes(StandardCharsets.UTF_8));


        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
