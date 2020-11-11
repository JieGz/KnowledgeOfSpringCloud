package com.demo.elastic.job;

import com.qiyue.mq.task.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * todo elastic job这个框架目前正在apache孵化中,目前2.1.5版本对zookeeper curator版本依赖过旧,可以考虑xxl-job
 */
@EnableElasticJob
@SpringBootApplication
public class ElasticJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticJobApplication.class, args);
    }
}
