package com.demo.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RefreshScope
@RestController
public class PaymentController {

    @Value(value = "${server.port}")
    private String port;

    @Value(value = "${config.info}")
    private String info;

    @Value(value = "${company.name}")
    private String name;

    @Value(value = "${company.date}")
    private String date;

    @Value(value = "${company.member}")
    private String member;

    @Value(value = "${company.money}")
    private String money;

    @GetMapping("/v1/test")
    public String test() {
        return info + "---" + name + "---" + date + "---" + member + "---" + money+ "---" + port;
    }

    @GetMapping("/v1/test/timeout")
    public String testTimeOut() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return info + "---" + name + "---" + date + "---" + member + "---" + money+ "---" + port;
    }
}




