package com.demo.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.demo.order.service.feigns.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class OrderService {

    @Value(value = "${server.info}")
    private String serverInfo;

    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //如果按照url地进行限流,那么会返回sentinel默认的错误返回
    //如果需要自定义返回错误的消息,那么只能通过@SentinelResource,blockHandler
    @SentinelResource(value = "buy", blockHandler = "buyBlockHandler")
    public String buy() {
        return paymentService.paymentTest();
    }

    public String buyBlockHandler(BlockException exception) {
        return exception.getClass().getName() + "<==>" + "服务不可用";
    }

    public String buyTimeOut() {
        return paymentService.paymentTestTimeOut() + ">>>>>" + serverInfo;
    }
}
