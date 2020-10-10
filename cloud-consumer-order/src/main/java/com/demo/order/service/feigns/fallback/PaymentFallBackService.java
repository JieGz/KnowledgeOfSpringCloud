package com.demo.order.service.feigns.fallback;

import com.demo.order.service.feigns.PaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallBackService implements PaymentService {
    @Override
    public String paymentTest() {
        return "返回兜底数据,限流降级技术由sentinel来支持:PaymentFallBackService#paymentTest";
    }

    @Override
    public String paymentTestTimeOut() {
        return "返回兜底数据,限流降级技术由sentinel来支持:PaymentFallBackService#paymentTestTimeOut";
    }
}
