package com.demo.payment.web;

import com.demo.payment.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@RestController
public class PaymentController {

    @Resource
    private PayService payService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/v1/test")
    public String test() {
        final String value = redisTemplate.opsForValue().get("a");
        System.out.println(value);

        return payService.getInfo();
    }

    @GetMapping("/v1/test/timeout")
    public String testTimeOut() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return payService.getInfo();
    }
}




