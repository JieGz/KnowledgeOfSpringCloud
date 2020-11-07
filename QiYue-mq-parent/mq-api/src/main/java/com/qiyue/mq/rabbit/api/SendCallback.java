package com.qiyue.mq.rabbit.api;

/**
 * 监听消息的回调函数
 */
public interface SendCallback {

    void onSucceed();


    void onFailure();
}
