package com.demo.elastic.job.test;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.qiyue.mq.task.annotation.ElasticJobConfig;
import org.springframework.stereotype.Component;

@Component
@ElasticJobConfig(
        cron = "0/5 * * * * ?",
        description = "测试定时任务",
        overwrite = true,
        shardingTotalCount = 5)
public class TestSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("66666666666666666----TestSimpleJob");
    }
}
