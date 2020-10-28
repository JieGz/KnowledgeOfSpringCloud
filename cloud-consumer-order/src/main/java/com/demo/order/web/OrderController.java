package com.demo.order.web;

import com.demo.order.entities.Order;
import com.demo.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @GetMapping("/v1/consumer/order/create")
    public String createOrder(@RequestParam("orderMoney") BigDecimal orderMoney, @RequestParam("userId") int userId) {
        final String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        final int result = orderService.create(Order.builder()
                .orderId(orderId)
                .orderState(0)
                .orderMoney(orderMoney)
                .userId(userId)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());
        return orderId + " result:" + result;
    }

    @GetMapping("/v1/consumer/order/state")
    public int updateOrderState(@RequestParam("orderId") String orderId, @RequestParam("orderState") int orderState) {
        return orderService.updateOrderState(orderId, orderState);
    }
}
