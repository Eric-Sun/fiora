package com.j13.fiora.jax;

import com.alibaba.fastjson.JSON;
import com.j13.fiora.core.exception.ErrorResponseException;

public class ResponseParser {


    public static <T> T parse(String s, Class<T> clazz) throws ErrorResponseException {
        ErrorResponse errorResponse = JSON.parseObject(s, ErrorResponse.class);
        if (errorResponse.getCode() != ErrorResponse.NO_ERROR) {
            throw new ErrorResponseException(errorResponse.getCode());
        }

        T response = JSON.parseObject(s, clazz);
        return response;
    }
}

class ErrorResponse {


    public static int NO_ERROR = 0;

    public ErrorResponse() {
    }

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