package com.j13.fiora.api.jax;

public class ErrorResponse {


    public static int NO_ERROR = 0;

    public ErrorResponse(){}

    public ErrorResponse(int code) {
        this.code = code;
    }

    private int code = NO_ERROR;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
