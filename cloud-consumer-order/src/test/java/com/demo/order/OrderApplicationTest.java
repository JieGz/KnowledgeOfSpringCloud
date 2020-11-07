package com.demo.order;

import com.qiyue.mq.core.producer.broker.RabbitMQProducerClient;
import com.qiyue.mq.rabbit.api.Message;
import com.qiyue.mq.rabbit.api.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTest {

    @Autowired
    private RabbitMQProducerClient rabbitMQProducerClient;

    @Test
    public void testProducerClient() throws Exception {

        for (int i = 0; i < 50; i++) {
            String uniqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            Message message = new Message(
                    uniqueId,
                    "luke-mq-tet",
                    "springboot.abc",
                    attributes,
                    0, MessageType.RELIABILITY);
//			message.setDelayMills(15000);
            rabbitMQProducerClient.send(message);
            Thread.sleep(1000L);
        }

        Thread.sleep(100000);
    }

    @Test
    public void testProducerClient2() throws Exception {

        for (int i = 0; i < 1; i++) {
            String uniqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "李四");
            attributes.put("age", "20");
            Message message = new Message(
                    uniqueId,
                    "delay-exchange",
                    "delay.abc",
                    attributes,
                    15000, MessageType.RELIABILITY);
            rabbitMQProducerClient.send(message);
        }

        Thread.sleep(100000);
    }

}