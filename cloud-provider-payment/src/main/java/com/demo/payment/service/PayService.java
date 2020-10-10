package com.demo.payment.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
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
}
