package com.demo.order.mapper;

import com.demo.order.entities.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     *
     * @param order 订单实体
     * @return 创建订单的情况
     */
    int createOrder(@Param("order") Order order);

    /**
     * 通过订单id查询单
     *
     * @param orderId 订单号
     * @return 订单实体
     */
    Order getOrderByOrderId(@Param("orderId") String orderId);

    /**
     * 通过订单id更新订单的状态
     *
     * @param orderId    订单号
     * @param orderState 订单状态
     * @return 更新情况
     */
    int updateOrderByOrderId(@Param("orderId") String orderId, @Param("orderState") Integer orderState);

}
