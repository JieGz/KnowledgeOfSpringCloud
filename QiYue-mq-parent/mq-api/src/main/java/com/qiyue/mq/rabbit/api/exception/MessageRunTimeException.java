package com.qiyue.mq.rabbit.api.exception;

public class MessageRunTimeException extends RuntimeException {
    private static final long serialVersionUID = -1335723345520153984L;

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String describe) {
        super(describe);
    }


    public MessageRunTimeException(String describe, Throwable cause) {
        super(describe, cause);
    }


    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
