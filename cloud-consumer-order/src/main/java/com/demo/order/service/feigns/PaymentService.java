package com.demo.order.service.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "knowledge-of-spring-cloud")
public interface PaymentService {

    @GetMapping("/v1/test")
    String paymentTest();
}
