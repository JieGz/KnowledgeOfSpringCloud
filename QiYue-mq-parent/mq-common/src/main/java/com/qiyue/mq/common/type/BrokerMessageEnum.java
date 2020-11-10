package com.qiyue.mq.common.type;

public enum BrokerMessageEnum {
    INIT("0"),
    SUCCEED("1"),
    Failure("2"),
    AFTER_MINUTER("3");

    private String type;

    BrokerMessageEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
