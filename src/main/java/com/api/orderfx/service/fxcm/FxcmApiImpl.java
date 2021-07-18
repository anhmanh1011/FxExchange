package com.api.orderfx.service.fxcm;

import api.message.codes.TRADE_OPERATION_CODE;
import api.message.codes.TRADE_TRANSACTION_TYPE;
import api.message.command.APICommandFactory;
import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.records.TradeTransInfoRecord;
import api.message.response.APIErrorResponse;
import api.message.response.TradeTransactionResponse;
import com.api.orderfx.ApplicationListener.XTBSocketConnect;
import com.api.orderfx.RestClientRequest.FXCMRequestClient;
import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.Utils.ObjectMapperUtils;
import com.api.orderfx.common.FxcmUtils;
import com.api.orderfx.model.fxcm.request.CreateEntryOrderRequest;
import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.model.fxcm.response.DataCommonFxcm;
import com.api.orderfx.model.fxcm.response.OrderFxcmResponse;
import com.api.orderfx.model.fxcm.response.ResponseFxcmStatus;
import com.api.orderfx.model.fxcm.response.ResponseRootFxcm;
import com.api.orderfx.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class FxcmApiImpl implements IFxcmApi {

    @Autowired
    FXCMRequestClient fxcmRequestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;


    @SneakyThrows
    @Override
    public ResponseRootFxcm getModel(String type) {
        try {
            LinkedHashMap linkedHashMap = fxcmRequestClient.sendGetRequest(FxcmUtils.GET_MODEL_BASE + type);
            ResponseRootFxcm rootFxcm = new ResponseRootFxcm();
            ResponseFxcmStatus responseFxcmStatus = ObjectMapperUtils.convert(linkedHashMap.get("response"), ResponseFxcmStatus.class);
            rootFxcm.setResponse(responseFxcmStatus);
            if (type.equals(FxcmUtils.GET_ORDER_PARAM)) {
                List<OrderFxcmResponse> orderFxcmResponses = ObjectMapperUtils.convertList(linkedHashMap.get("orders"), new TypeReference<List<OrderFxcmResponse>>() {
                });
                rootFxcm.setData(orderFxcmResponses);
            }
            return rootFxcm;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(JsonUtils.ObjectToJson(ex));
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public TradeTransactionResponse createOder(CreateOrderRequest createOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException {
        try {
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();


            TradeTransInfoRecord tradeTransInfoRecord = new TradeTransInfoRecord();
            tradeTransInfoRecord.setCmd(createOrderRequest.getIsBuy() ? TRADE_OPERATION_CODE.BUY_STOP : TRADE_OPERATION_CODE.SELL_STOP);
            tradeTransInfoRecord.setCustomComment("tradding with api");
            tradeTransInfoRecord.setExpiration(dt.getTime());
            tradeTransInfoRecord.setPrice(createOrderRequest.getPrice());
            tradeTransInfoRecord.setSl(createOrderRequest.getStop());
            if (createOrderRequest.getIsBuy()) {
                tradeTransInfoRecord.setTp(createOrderRequest.getLimit().stream().mapToDouble(value -> value).max().orElseThrow());
            } else {
                tradeTransInfoRecord.setTp(createOrderRequest.getLimit().stream().mapToDouble(value -> value).min().orElseThrow());
            }
            tradeTransInfoRecord.setVolume(0.01);
            tradeTransInfoRecord.setSymbol(createOrderRequest.getSymbols());
            tradeTransInfoRecord.setType(TRADE_TRANSACTION_TYPE.OPEN);
            TradeTransactionResponse tradeTransactionResponse = APICommandFactory.executeTradeTransactionCommand(XTBSocketConnect.connector, tradeTransInfoRecord);
            return tradeTransactionResponse;


        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }


    private CreateEntryOrderRequest convertOrderRequestToEntryRequest(CreateOrderRequest createOrderRequest) {
        CreateEntryOrderRequest createEntryOrderRequest = new CreateEntryOrderRequest();
        createEntryOrderRequest.setSymbol(createOrderRequest.getSymbols());
        createEntryOrderRequest.setIs_buy(createOrderRequest.getIsBuy());
        createEntryOrderRequest.setRate(createOrderRequest.getPrice());
        createEntryOrderRequest.setStop(createOrderRequest.getStop());
        createEntryOrderRequest.setLimit(createOrderRequest.getLimit().stream().mapToDouble(value -> value).max().orElseThrow());
//        createEntryOrderRequest.setAmount(100);
        return createEntryOrderRequest;
    }

    private CreateEntryOrderRequest convertTrade(CreateOrderRequest createOrderRequest) {
        CreateEntryOrderRequest createEntryOrderRequest = new CreateEntryOrderRequest();
        createEntryOrderRequest.setSymbol(createOrderRequest.getSymbols());
        createEntryOrderRequest.setIs_buy(createOrderRequest.getIsBuy());
        createEntryOrderRequest.setStop(createOrderRequest.getStop());
        createEntryOrderRequest.setLimit(createOrderRequest.getLimit().stream().mapToDouble(value -> value).max().orElseThrow());
        createEntryOrderRequest.setAmount(createOrderRequest.getAmount());
        createEntryOrderRequest.setOrder_type("AtMarket");
        return createEntryOrderRequest;
    }

}
