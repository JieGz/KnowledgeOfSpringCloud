package com.demo.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ProducerSimpleTest {

    private String queue = "mq-test";


    @Test
    public void testSendMessage() {


        try {
            //1. 创建连接RabbitMQ的连接工厂对象
            final ConnectionFactory connectionFactory = new ConnectionFactory();
            //2. 设置连接RabbitMQ的主机和端口
            connectionFactory.setHost("192.168.56.100");
            connectionFactory.setPort(5672);
            //3. 先到控制台创建一个虚拟主机
            //3.1 设置连接虚拟主机
            connectionFactory.setVirtualHost("test");
            //4 先到控制台创建一个用户并绑定到虚拟主机
            //4.1 设置访问虚拟主机的用户和密码
            connectionFactory.setUsername("luke");
            connectionFactory.setPassword("luke");

            //5. 通过连接工厂获取连接对象
            final Connection connection = connectionFactory.newConnection();

            //6. 通过连接创建连接通道
            final Channel channel = connection.createChannel();

            //7. 通过通道绑定对应的消息对列
            //参数1：队列的名称，如果队列不存在，会自动创建
            //参数2：用来定义队列的特性是否要持久化
            //参数3：表示是否不独占队列
            //参数4：表示消息费消费消息后是否自动删除队列
            //参数5：额外附加的参数¬
            channel.queueDeclare(queue, false, false, false, null);

            String msg = "Hello,RabbitMQ,I'm Luke,I'm coming.";
            //8. 发送消息
            //参数1：交换机的名称，""表示直连模式: provider-->queue
            //参数2：队列的名称
            //参数3：传递消息的额外属性
            //参数4：发送的内容
            channel.basicPublish("", queue, null, msg.getBytes(StandardCharsets.UTF_8));

            //9. 关闭通道
            channel.close();
            //10. 关闭连接
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
