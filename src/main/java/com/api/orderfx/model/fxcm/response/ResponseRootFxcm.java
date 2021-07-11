package com.api.orderfx.model.fxcm.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRootFxcm<T> {
    public ResponseFxcmStatus response;
    public T data;
}
