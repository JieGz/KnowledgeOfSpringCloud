package com.demo.order.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class GlobalBlockHandler {

    //important 如果方法没有被static修饰,那么将无法接管限流异常
    public String globalHandlerBlockException(BlockException exception) {
        return exception.getClass().getName() + "限流功能由全局统一的异常接管,globalHandlerBlockException---1";
    }

    public static String globalHandlerBlockException2(BlockException exception) {
        return exception.getClass().getName() + "限流功能由全局统一的异常接管,globalHandlerBlockException---2";
    }
}
