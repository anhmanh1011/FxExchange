package com.api.orderfx.service.fxcm;

import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.TradeTransactionResponse;
import com.api.orderfx.model.fxcm.request.CreateEntryOrderRequest;
import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.model.fxcm.response.ResponseRootFxcm;

public interface IFxcmApi {
    ResponseRootFxcm getModel(String type);
    TradeTransactionResponse createOder(CreateOrderRequest createOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException;
}
