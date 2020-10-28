package com.demo.order.entities;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
public class Order {
    private String orderId;
    private int orderState;
    private int userId;
    private BigDecimal orderMoney;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
