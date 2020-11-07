package com.qiyue.mq.core.producer.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.qiyue.mq.core.producer.broker.RabbitBroker;
import com.qiyue.mq.core.producer.entities.BrokerMessage;
import com.qiyue.mq.core.producer.service.MessageStoreService;
import com.qiyue.mq.task.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@ElasticJobConfig(
        cron = "0/10 * * * * ?",
        description = "可靠性投递消息补偿任务",
        overwrite = true,
        shardingTotalCount = 1
)
@Slf4j
public class RetryMessageDataflowJob implements DataflowJob<BrokerMessage> {

    @Resource
    private MessageStoreService messageStoreService;

    @Resource
    private RabbitBroker rabbitBroker;

    private static final int MAX_RETRY_COUNT = 3;

    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        List<BrokerMessage> list = messageStoreService.fetchTimeOutMessage4Retry("0");
        log.info("--------@@@@@ 抓取数据集合, 数量：	{} 	@@@@@@-----------", list.size());
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> dataList) {

        dataList.forEach(brokerMessage -> {

            String messageId = brokerMessage.getMessageId();
            if (brokerMessage.getTryCount() >= MAX_RETRY_COUNT) {
                this.messageStoreService.failure(messageId);
                log.warn(" -----消息设置为最终失败，消息ID: {} -------", messageId);
            } else {
                //	每次重发的时候要更新一下try count字段
                this.messageStoreService.updateTryCount(messageId);
                // 	重发消息
                this.rabbitBroker.reliabilitySend(brokerMessage.getMessage());
            }

        });
    }


}
