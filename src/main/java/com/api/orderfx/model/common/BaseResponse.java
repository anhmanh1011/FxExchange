package com.api.orderfx.model.common;

import com.api.orderfx.common.EnumCodeResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BaseResponse<T> {
    int code;
    String message;
    T data;

    public BaseResponse(EnumCodeResponse enumCodeResponse) {
        this.code = enumCodeResponse.getCode();
        this.message = enumCodeResponse.getDescription();
    }

    public BaseResponse(HttpStatus status) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }

}
