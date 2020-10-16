package com.demo.payment.service;

import com.demo.payment.entites.Order;
import com.demo.payment.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;


    @Test
    public void test() {

        final Order order = orderService.getOrderById(1L);
        System.out.println(order);
    }


    @Test
    public void testMap() {
        Map<String, Object> map = new HashMap<>();

        // map.put("orderName", "买牙膏");
        // map.put("orderUser", "Luke");

        System.out.println(orderMapper.getOrder(map));
    }

    @Test
    public void testUpdate() {
        Map<String, Object> map = new HashMap<>();

        map.put("orderId", 1);
        map.put("orderState", 2);
        map.put("orderName", "买牙膏1");
        map.put("orderUser", "Luke1");

        System.out.println(orderMapper.updateOrder(map));
    }
}