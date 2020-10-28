package com.demo.payment.service.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${service.order.name}")
public interface OrderService {

    @GetMapping("/v1/consumer/order/state")
    int updateOrderState(@RequestParam("orderId") String orderId, @RequestParam("orderState") int orderState);
}
