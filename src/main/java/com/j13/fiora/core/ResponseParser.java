package com.j13.fiora.core;

import com.alibaba.fastjson.JSON;
import com.j13.fiora.api.jax.ErrorResponse;
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
