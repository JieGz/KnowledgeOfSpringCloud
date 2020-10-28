package com.demo.payment.service;

import com.demo.payment.entites.User;
import com.demo.payment.mapper.UserMapper;
import com.demo.payment.service.feigns.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@RefreshScope
@Slf4j
public class PayService {

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

    public String getInfo() {
        return info + "---" + name + "---" + date + "---" + member + "---" + money + "---" + port + "---";
    }

    @Resource
    private UserMapper userMapper;

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public int pay(int userId, String orderId, BigDecimal orderMoney) {
        final User user = userMapper.getUserById(userId);
        if (user.getMoney().compareTo(orderMoney) < 0) {
            throw new RuntimeException("用户余额不足");
        }
        //修改用户的余额
        user.setMoney(user.getMoney().subtract(orderMoney));
        userMapper.updateUser(user);
        //修改订单状态
        final int state = orderService.updateOrderState(orderId, 1);

        log.info("state:{}", state);

        return state;
    }


}
