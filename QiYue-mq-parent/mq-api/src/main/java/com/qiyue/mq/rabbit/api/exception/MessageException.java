package com.qiyue.mq.rabbit.api.exception;

public class MessageException extends Exception {
    private static final long serialVersionUID = 2698842029697343877L;

    public MessageException() {
        super();
    }

    public MessageException(String describe) {
        super(describe);
    }


    public MessageException(String describe, Throwable cause) {
        super(describe, cause);
    }


    public MessageException(Throwable cause) {
        super(cause);
    }
}
