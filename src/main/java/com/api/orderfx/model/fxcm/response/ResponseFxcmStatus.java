package com.api.orderfx.model.fxcm.response;

import lombok.Data;

@Data
public class ResponseFxcmStatus {
    public Boolean executed;
    public String  error;
}
