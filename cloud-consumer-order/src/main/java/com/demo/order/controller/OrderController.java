package com.demo.order.controller;

import com.demo.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/v1/consumer/buy")
    public String buy() {
        return orderService.buy();
    }

    @GetMapping("/v1/consumer/best/buy")
    public String bestBuy() {
        return orderService.bestBuy();
    }

    @GetMapping("/v1/consumer/buy/timeout")
    public String buyTimeOut() {
        return orderService.buyTimeOut();
    }
}
