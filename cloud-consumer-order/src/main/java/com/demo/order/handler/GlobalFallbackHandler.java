package com.demo.order.handler;


public class GlobalFallbackHandler {
    //important 如果方法没有被static修饰,那么将无法接管限流异常
    public String globalHandlerFallbackException() {
        return "Runtime Exception和业务异常由全局统一的GlobalFallbackHandler接管,GlobalFallbackHandler---1";
    }

    public static String globalHandlerFallbackException2() {
        return "Runtime Exception和业务异常由全局统一的GlobalFallbackHandler接管,GlobalFallbackHandler---2";
    }
}
