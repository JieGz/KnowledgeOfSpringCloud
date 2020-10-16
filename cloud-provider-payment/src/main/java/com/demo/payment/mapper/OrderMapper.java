package com.demo.payment.mapper;

import com.demo.payment.entites.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    Order getOrderById(@Param("orderId") Long id);

    List<Order> getOrder(Map<String, Object> map);
}
