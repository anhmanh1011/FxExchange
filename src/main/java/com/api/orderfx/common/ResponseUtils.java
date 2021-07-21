package com.api.orderfx.common;

import com.api.orderfx.model.common.BaseResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtils {
    public BaseResponse created() {
        return new BaseResponse(EnumCodeResponse.SUCCESS);
    }
}
