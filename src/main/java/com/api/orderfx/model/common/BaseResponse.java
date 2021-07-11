package com.api.orderfx.model.common;

import lombok.Data;

import java.util.Date;

@Data
public class BaseResponse<T> {

    public Date timestamp;
    public String errorCode;
    public String message;
    public String path;
    public T data;

}
