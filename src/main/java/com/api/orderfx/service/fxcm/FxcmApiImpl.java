package com.api.orderfx.service.fxcm;

import api.message.codes.TRADE_OPERATION_CODE;
import api.message.codes.TRADE_TRANSACTION_TYPE;
import api.message.command.APICommandFactory;
import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.records.TradeRecord;
import api.message.records.TradeTransInfoRecord;
import api.message.response.*;
import com.api.orderfx.ApplicationListener.XTBSocketConnect;
import com.api.orderfx.RestClientRequest.FXCMRequestClient;
import com.api.orderfx.Utils.ConverterUtils;
import com.api.orderfx.common.ResponseUtils;
import com.api.orderfx.entity.TradeTransInfoEntity;
import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.model.common.BaseResponse;
import com.api.orderfx.repository.OrderRepository;
import com.api.orderfx.repository.TradeTransInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class FxcmApiImpl implements ITradeApi {

    @Autowired
    FXCMRequestClient fxcmRequestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TradeTransInfoRepository tradeTransInfoRepository;


    @Override
    public BaseResponse openTrade(CreateOrderRequest createOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException {
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
            tradeTransInfoRecord.setSl(createOrderRequest.getStop());
            SymbolResponse symbol = getSymbol(createOrderRequest.getSymbols());
            log.info(symbol.toString());
            if (createOrderRequest.getIsBuy()) {
                tradeTransInfoRecord.setTp(createOrderRequest.getLimit().stream().mapToDouble(value -> value).max().orElseThrow());
                tradeTransInfoRecord.setPrice(symbol.getSymbol().getAsk());
            } else {
                tradeTransInfoRecord.setPrice(symbol.getSymbol().getBid());
                tradeTransInfoRecord.setTp(createOrderRequest.getLimit().stream().mapToDouble(value -> value).min().orElseThrow());
            }
            tradeTransInfoRecord.setVolume(0.01);
            tradeTransInfoRecord.setSymbol(createOrderRequest.getSymbols());
            tradeTransInfoRecord.setType(TRADE_TRANSACTION_TYPE.OPEN);
            TradeTransactionResponse tradeTransactionResponse = APICommandFactory.executeTradeTransactionCommand(XTBSocketConnect.connector, tradeTransInfoRecord);
            if (tradeTransactionResponse.getStatus()) {
                Long order = tradeTransactionResponse.getOrder();
                TradeRecordsResponse tradeRecordsResponse = APICommandFactory.executeTradeRecordsCommand(XTBSocketConnect.connector, Collections.singletonList(order));
                TradeRecord tradeRecord = tradeRecordsResponse.getTradeRecords().get(0);
                TradeTransInfoEntity tradeTransInfoEntity = ConverterUtils.tradeRecordInfoToTradeTransInfoEntity(tradeRecord);
                tradeTransInfoRepository.save(tradeTransInfoEntity);
                return ResponseUtils.created();
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public SymbolResponse getSymbol(String symbol) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException {
        return APICommandFactory.executeSymbolCommand(XTBSocketConnect.connector, symbol);
    }


}
