package com.knowledge.rabbitmq.delay.web;

import com.knowledge.rabbitmq.delay.constants.DelayTypeEnum;
import com.knowledge.rabbitmq.delay.mq.DelayMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequestMapping("rabbitmq")
@RestController
public class RabbitMQMsgController {

    @Resource
    private DelayMessageSender sender;

    @RequestMapping("sendmsg")
    public void sendMsg(String msg, Integer delayType) {
        log.info("当前时间：{},收到请求，msg:{},delayType:{}", LocalDateTime.now(), msg, delayType);
        sender.sendMessage(msg, Objects.requireNonNull(DelayTypeEnum.getDelayTypeEnumByValue(delayType)));
    }

    @RequestMapping("delayMsg")
    public void delayMsg(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", LocalDateTime.now(), msg, delayTime);
        sender.sendMsg(msg, delayTime);
    }

    @RequestMapping("delayMsg2")
    public void delayMsg2(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", LocalDateTime.now(), msg, delayTime);
        sender.sendDelayMsg(msg, delayTime);
    }
}