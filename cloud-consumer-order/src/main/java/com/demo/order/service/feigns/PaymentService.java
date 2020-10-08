package com.demo.order.service.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//name的值可以通过配置中心来管理
@FeignClient(name = "${service.payment.name}")
public interface PaymentService {

    @GetMapping("/v1/test")
    String paymentTest();

    @GetMapping("/v1/test/timeout")
    String paymentTestTimeOut();
}
