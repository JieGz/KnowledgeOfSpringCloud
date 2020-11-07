package com.demo.elastic.job;

import com.qiyue.mq.task.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableElasticJob
@SpringBootApplication
public class ElasticJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticJobApplication.class, args);
    }
}
