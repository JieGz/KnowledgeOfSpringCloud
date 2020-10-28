package com.demo.payment.service.dubbo;

import com.demo.ISayHelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;


@Slf4j
@DubboService
public class SayHelloServiceImpl implements ISayHelloService {

    @Override
    public String sayHello(String name) {
        return "Hello," + name + ",I'm SpringBoot Dubbo provider.";
    }
}
