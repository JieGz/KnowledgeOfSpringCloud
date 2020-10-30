package com.demo.rabbitmq.fanout;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ConsumerFanoutTest {

    private String exchange = "exchange-luke";

    @Test
    public void fanoutConsumerMsg() {
        try {
            final ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.56.100");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("test");
            connectionFactory.setUsername("luke");
            connectionFactory.setPassword("luke");

            final Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();

            //1.channel要绑定交换机
            channel.exchangeDeclare(exchange, "fanout");

            //2. 通过channel创建临时对列
            final String queue = channel.queueDeclare().getQueue();

            //3. 通过channel将交换机绑定临时对列
            channel.queueBind(queue, exchange, "");

            //4. 通过channel消费消息
            channel.basicConsume(queue, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消费者1：" + new String(body, StandardCharsets.UTF_8));
                    //通过channel手动确认消费
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });

            TimeUnit.MINUTES.sleep(5);

            //9. 关闭通道
            channel.close();
            //10. 关闭连接
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
