package com.api.orderfx.service;

import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.SymbolResponse;
import com.api.orderfx.common.BaseException;
import com.api.orderfx.model.common.BaseResponse;
import com.api.orderfx.model.fxcm.request.CloseOrderRequest;
import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.model.fxcm.request.ModifyOrderRequest;

public interface ITradeApi {

    BaseResponse openPosition(CreateOrderRequest createOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException;

    BaseResponse modifyPosition(ModifyOrderRequest modifyOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException;

    BaseResponse closePosition(CloseOrderRequest closeOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException;

    SymbolResponse getSymbol(String symbol) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException;
}
