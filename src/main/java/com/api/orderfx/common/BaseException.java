package com.api.orderfx.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends Exception {

    int code;
    String message;

    public BaseException(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public BaseException(EnumCodeResponse enumCodeResponse) {
        this.code = enumCodeResponse.getCode();
        this.message = enumCodeResponse.getDescription();
    }
}
