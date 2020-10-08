package com.demo.order.controller;

import com.demo.order.service.feigns.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RefreshScope
@RestController
public class OrderController {

    @Value(value = "${server.info}")
    private String serverInfo;

    @Resource
    private PaymentService paymentService;

    @GetMapping("/v1/consumer/buy")
    public String buy() {
        return paymentService.paymentTest() + "<===>" + serverInfo;
    }
}
