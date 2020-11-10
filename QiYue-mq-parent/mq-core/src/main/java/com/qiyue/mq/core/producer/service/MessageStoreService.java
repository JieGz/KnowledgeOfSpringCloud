package com.qiyue.mq.core.producer.service;

import com.qiyue.mq.common.type.BrokerMessageEnum;
import com.qiyue.mq.core.producer.entities.BrokerMessage;
import com.qiyue.mq.core.producer.mapper.BrokerMessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageStoreService {

    @Resource
    private BrokerMessageMapper brokerMessageMapper;


    /**
     * 向本地消息表插入消息
     *
     * @param record 消息
     * @return 插入消息的状态
     */
    public int insert(BrokerMessage record) {
        return brokerMessageMapper.insert(record);
    }

    public void succeed(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageEnum.SUCCEED.type(), LocalDateTime.now());
    }

    public void failure(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageEnum.Failure.type(), LocalDateTime.now());
    }

    public void sendAfterMinutes(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageEnum.AFTER_MINUTER.type(), LocalDateTime.now());
    }

    public BrokerMessage selectByMessageId(String messageId) {
        return this.brokerMessageMapper.selectByPrimaryKey(messageId);
    }

    public List<BrokerMessage> fetchTimeOutMessage4Retry(String code) {
        return this.brokerMessageMapper.queryBrokerMessageStatus4Timeout(code);
    }

    public int updateTryCount(String brokerMessageId) {
        return this.brokerMessageMapper.update4TryCount(brokerMessageId, LocalDateTime.now());
    }
}
