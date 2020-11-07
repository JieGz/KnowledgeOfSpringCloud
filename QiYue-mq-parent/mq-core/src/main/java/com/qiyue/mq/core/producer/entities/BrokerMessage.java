package com.qiyue.mq.core.producer.entities;

import com.qiyue.mq.rabbit.api.Message;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BrokerMessage implements Serializable {
    private static final long serialVersionUID = 1356529610885190328L;

    private String messageId;

    private Message message;

    private int tryCount = 0;

    private String status;

    private LocalDateTime nextRetry;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
