package com.demo.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.demo.order.entities.Order;
import com.demo.order.handler.GlobalBlockHandler;
import com.demo.order.handler.GlobalFallbackHandler;
import com.demo.order.mapper.OrderMapper;
import com.demo.order.service.feigns.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@RefreshScope
public class OrderService {

    @Value(value = "${server.info}")
    private String serverInfo;

    @Value("${server.port}")
    private String serverPort;

    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //如果按照url地进行限流,那么会返回sentinel默认的错误返回
    //如果需要自定义返回错误的消息,那么只能通过@SentinelResource,blockHandler
    //问题:这种方法的问题就是,业务复杂代码膨胀的特别利害.解决方案使用统一的限流流管理
    //fallback管理运行时异常
    //blockHandler管理配置异常
    @SentinelResource(value = "buy", blockHandler = "buyBlockHandler")
    public String buy() {
        return paymentService.paymentTest() + ">>>>" + serverInfo + ">>> order server port:" + serverPort;
    }

    public String buyBlockHandler(BlockException exception) {
        return exception.getClass().getName() + "<==>" + "服务不可用";
    }

    //这种方法是更好的选择,但是注意blockHandler和fallback的方法,需要被 static修饰
    //这里的fallback只管本微服务的业务异常和运行时异常
    //而@FeignClient配置的异常管接口的异常
    @SentinelResource(value = "bestBuy",
            fallbackClass = GlobalFallbackHandler.class,
            fallback = "globalHandlerFallbackException2",
            blockHandlerClass = GlobalBlockHandler.class,
            blockHandler = "globalHandlerBlockException2")
    public String bestBuy() {
        return paymentService.paymentTest() + ">>>>>" + serverInfo + ">>> order server port:" + serverPort;
    }

    public String buyTimeOut() {
        return paymentService.paymentTestTimeOut() + ">>>>>" + serverInfo + ">>> order server port:" + serverPort;
    }

    /**********************************************Business**************************************************************/

    @Resource
    private OrderMapper orderMapper;

    public int create(Order order) {
        System.out.println(order.getOrderId());
        return orderMapper.createOrder(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateOrderState(String orderId, int orderSates) {
        return orderMapper.updateOrderByOrderId(orderId, orderSates);
    }
}
