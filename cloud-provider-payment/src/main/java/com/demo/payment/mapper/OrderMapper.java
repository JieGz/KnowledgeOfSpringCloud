package com.demo.payment.mapper;

import com.demo.payment.entites.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    public Order getOrderById(@Param("orderId") Long id);
}
