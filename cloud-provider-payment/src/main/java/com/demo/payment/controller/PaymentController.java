package com.demo.payment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RefreshScope
@RestController
public class PaymentController {

    @Value(value = "${config.info}")
    private String info;

    @GetMapping("/v1/test")
    public String test() {
        log.info("test " + info);
        return "test ===>" + info;
    }
}




