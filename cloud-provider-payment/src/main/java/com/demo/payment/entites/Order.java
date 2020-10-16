package com.demo.payment.entites;


import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {
    private Long orderId;
    private int orderState;
    private String orderName;
    private String orderUser;
}
