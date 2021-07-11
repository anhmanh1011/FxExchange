package com.api.orderfx.service.fxcm;

import com.api.orderfx.model.fxcm.request.CreateEntryOrderRequest;
import com.api.orderfx.model.fxcm.response.ResponseRootFxcm;

public interface IFxcmApi {
    ResponseRootFxcm getModel(String type);
    ResponseRootFxcm createOder(CreateEntryOrderRequest createEntryOrderRequest);
}
