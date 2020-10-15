package com.demo.payment.service;

import com.demo.payment.entites.Order;
import com.demo.payment.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;


    public Order getOrderById(Long id) {
        return orderMapper.getOrderById(id);
    }
}
