package com.j13.fiora.core.exception;

public class RequestFatalException extends RuntimeException {

    public RequestFatalException(String msg, Throwable t) {
        super(msg, t);
    }

    public RequestFatalException(String msg) {
        super(msg);
    }


}
