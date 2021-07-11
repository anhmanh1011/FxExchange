package com.api.orderfx.common;

import lombok.Data;

@Data
public class BaseException extends Exception {

    int code;
    String message;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
}
