package com.demo.payment.service;

import com.demo.payment.entites.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;


    @Test
    public void test() {
        final Order order = orderService.getOrderById(1L);
        System.out.println(order);
    }
}