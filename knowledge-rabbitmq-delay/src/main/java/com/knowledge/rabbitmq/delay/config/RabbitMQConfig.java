package com.knowledge.rabbitmq.delay.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String DELAY_EXCHANGE_NAME = "delay.queue.demo.business.exchange";

    public static final String DELAY_QUEUEA_NAME = "delay.queue.demo.business.queuea";
    public static final String DELAY_QUEUEB_NAME = "delay.queue.demo.business.queueb";

    public static final String DELAY_QUEUEA_ROUTING_KEY = "delay.queue.demo.business.queuea.routingkey";
    public static final String DELAY_QUEUEB_ROUTING_KEY = "delay.queue.demo.business.queueb.routingkey";

    public static final String DEAD_LETTER_EXCHANGE = "delay.queue.demo.deadletter.exchange";

    public static final String DEAD_LETTER_QUEUEA_ROUTING_KEY = "delay.queue.demo.deadletter.delay_10s.routingkey";
    public static final String DEAD_LETTER_QUEUEB_ROUTING_KEY = "delay.queue.demo.deadletter.delay_60s.routingkey";

    public static final String DEAD_LETTER_QUEUEA_NAME = "delay.queue.demo.deadletter.queuea";
    public static final String DEAD_LETTER_QUEUEB_NAME = "delay.queue.demo.deadletter.queueb";

    //普通队列,通过给消息设置延时时间
    public static final String DELAY_EXCHANGEC_NAME = "delay.queue.demo.business.exchange";

    public static final String DELAY_QUEUEC_NAME = "delay.queue.demo.business.queuec";

    public static final String DELAY_QUEUEC_ROUTING_KEY = "delay.queue.demo.business.queuec.routingkey";

    public static final String DEAD_LETTER_EXCHANGE_C = "delay.queue.demo.deadletter.exchange";

    public static final String DEAD_LETTER_QUEUEC_ROUTING_KEY = "delay.queue.demo.deadletter.delay_anytime.routingkey";

    public static final String DEAD_LETTER_QUEUEC_NAME = "delay.queue.demo.deadletter.queuec";


    //声明延时交换机
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    //声明死信交换机 
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    //声明延时队列A,延时6秒,并绑定到死信交换机上
    @Bean
    public Queue delayQueueA() {
        Map<String, Object> args = new HashMap<>(4);
        //x-dead-letter-exchange 声明当前队列与死信交换机绑定
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        //x-dead-letter-routing-key 声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEA_ROUTING_KEY);
        //x-message-ttl 声明当前队列的消息的最大存活时间TTL(Time To Live)
        args.put("x-message-ttl", 6000);
        return QueueBuilder.durable(DELAY_QUEUEA_NAME).withArguments(args).build();
    }

    //声明延时队列B,延时60秒,并与死信交换机绑定
    @Bean
    public Queue delayQueueB() {
        Map<String, Object> args = new HashMap<>(4);
        //x-dead-letter-exchange 声明当前队列与死信交换机绑定
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        //x-dead-letter-routing-key 声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEB_ROUTING_KEY);
        //x-message-ttl 声明当前队列的消息的最大存活时间TTL(Time To Live)
        args.put("x-message-ttl", 60000);
        return QueueBuilder.durable(DELAY_QUEUEB_NAME).withArguments(args).build();
    }

    //将延时队列A和延时交换机绑定
    @Bean
    public Binding delayBindingA(@Qualifier("delayQueueA") Queue queue, @Qualifier("delayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUEA_ROUTING_KEY);
    }

    //将延时队列B和延时交换机绑定
    @Bean
    public Binding delayBindingB(@Qualifier("delayQueueB") Queue queue, @Qualifier("delayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUEB_ROUTING_KEY);
    }

    //声明死信队列A
    @Bean
    public Queue deadLetterQueueA() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUEA_NAME).build();
    }

    //声明死信队列B
    @Bean
    public Queue deadLetterQueueB() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUEB_NAME).build();
    }

    //将死信队列A和死信交换机绑定
    @Bean
    public Binding deadLetterBindingA(@Qualifier("deadLetterQueueA") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEA_ROUTING_KEY);
    }

    //将死信队列B和死信交换机绑定
    @Bean
    public Binding deadLetterBindingB(@Qualifier("deadLetterQueueB") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEB_ROUTING_KEY);
    }

    //声明交换机C
    @Bean
    public DirectExchange delayExchangeC() {
        return new DirectExchange(DELAY_EXCHANGEC_NAME);
    }

    //声明死信交换机C
    @Bean
    public DirectExchange deadLetterExchangeC() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE_C);
    }

    //声明队列C,并将队列C和交换机绑定
    @Bean
    public Queue delayQueueC() {
        Map<String, Object> args = new HashMap<>(2);
        //x-dead-letter-exchange 声明当前队列与死信交换机绑定
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_C);
        //x-dead-letter-routing-key 声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEC_ROUTING_KEY);
        return QueueBuilder.durable(DELAY_QUEUEC_NAME).withArguments(args).build();
    }

    //将普通队列C和交换机C绑定
    @Bean
    public Binding delayBindingC(@Qualifier("delayQueueC") Queue queue, @Qualifier("delayExchangeC") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUEC_ROUTING_KEY);
    }

    //声明死信队列C
    @Bean
    public Queue deadLetterQueueC() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUEC_NAME).build();
    }

    //将死信队列C和死信交换机C绑定在一起
    @Bean
    public Binding deadLetterBindingC(@Qualifier("deadLetterQueueC") Queue queue, @Qualifier("deadLetterExchangeC") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEC_ROUTING_KEY);
    }

}
