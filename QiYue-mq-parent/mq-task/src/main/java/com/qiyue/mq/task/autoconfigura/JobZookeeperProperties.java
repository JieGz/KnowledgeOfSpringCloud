package com.qiyue.mq.task.autoconfigura;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "elastic.job.zk")
public class JobZookeeperProperties {

    private String serverLists;
    private String namespace;

    private int baseSleepTimeMilliseconds = 1000;
    private int maxSleepTimeMilliseconds = 3000;
    private int maxRetries = 3;

    private int sessionTimeoutMilliseconds = 60000;
    private int connectionTimeoutMilliseconds = 15000;
    private String digest;

}
