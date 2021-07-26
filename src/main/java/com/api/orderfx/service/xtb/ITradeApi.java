package com.api.orderfx.service.xtb;

import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.SymbolResponse;
import com.api.orderfx.common.BaseException;
import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.model.common.BaseResponse;

public interface ITradeApi {

    BaseResponse openTrade(CreateOrderRequest createOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException;

    SymbolResponse getSymbol(String symbol) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException;
}
