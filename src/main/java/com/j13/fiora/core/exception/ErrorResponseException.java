package com.j13.fiora.core.exception;

public class ErrorResponseException extends Exception {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ErrorResponseException(int errorCode, String message) {
        super(message);
        this.code = errorCode;
    }
    public ErrorResponseException(int errorCode) {
        this.code = errorCode;
    }

    public String toErrorString() {
        return "WO CAO JAX HAS PROBLEM!!!!. code = " + code;
    }
}
