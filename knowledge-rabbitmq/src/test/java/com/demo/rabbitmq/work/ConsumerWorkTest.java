package com.demo.rabbitmq.work;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ConsumerWorkTest {

    private String queue = "mq-test";

    @Test
    public void consumerMessage() {
        try {
            //1. 创建连接RabbitMQ的连接工厂对象
            final ConnectionFactory connectionFactory = new ConnectionFactory();
            //2. 设置RabbitMQ的主机和端口
            connectionFactory.setHost("192.168.56.100");
            connectionFactory.setPort(5672);
            //3. 连接虚拟主机
            connectionFactory.setVirtualHost("test");
            //4. 设置访问需要主机的帐号和密码
            connectionFactory.setUsername("luke");
            connectionFactory.setPassword("luke");

            //5. 通过连接工厂获取连接
            final Connection connection = connectionFactory.newConnection();

            //6. 通过连接创建连接通道
            final Channel channel = connection.createChannel();


            //7. 通过channel通道绑定需要消费的消息对象
            channel.queueDeclare(queue, false, false, false, null);
            //7.1 每次只消费一条消息
            channel.basicQos(1);


            //8.开始消费
            //8.1 消费的消息队列
            //8.2 自动确认消息是否已经被消费了，如果为false在后台会被成Unacked[消息自动确认机制]
            channel.basicConsume(queue, false, new DefaultConsumer(channel) {
                @Override//从body中取出消息的内容
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println(new String(body, StandardCharsets.UTF_8));
                    //手动确认
                    //参数1：确认队列中某个具体的消息
                    //参数2：是否开启开多个消息同时确认
                    //channel.basicAck(envelope.getDeliveryTag(), false);
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
