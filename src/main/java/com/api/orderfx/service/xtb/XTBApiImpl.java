package com.api.orderfx.service.xtb;

import api.message.codes.TRADE_OPERATION_CODE;
import api.message.codes.TRADE_TRANSACTION_TYPE;
import api.message.command.APICommandFactory;
import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.records.TradeTransInfoRecord;
import api.message.response.APIErrorResponse;
import api.message.response.SymbolResponse;
import api.message.response.TradeTransactionResponse;
import api.sync.SyncAPIConnector;
import com.api.orderfx.ApplicationListener.MetaApiSocketConnect;
import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.Utils.SortUtils;
import com.api.orderfx.Utils.TimeUtils;
import com.api.orderfx.common.*;
import com.api.orderfx.entity.PositionInfoEntity;
import com.api.orderfx.entity.ProfitManagementInfoEntity;
import com.api.orderfx.entity.SymbolsSubscribeEntity;
import com.api.orderfx.model.common.BaseResponse;
import com.api.orderfx.model.fxcm.request.CloseOrderRequest;
import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.model.fxcm.request.ModifyOrderRequest;
import com.api.orderfx.repository.PositionInfoRepository;
import com.api.orderfx.repository.ProfitManagementRepository;
import com.api.orderfx.repository.SymbolsSubscribeRepository;
import com.api.orderfx.service.ITradeApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(BrokerCode.XTB_EXCHANGE)
@Slf4j
public class XTBApiImpl implements ITradeApi {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PositionInfoRepository positionInfoRepository;
    @Autowired
    ProfitManagementRepository profitManagementRepository;

    @Autowired
    ConnectorFactory connectorFactory;

    @Autowired
    SymbolsSubscribeRepository symbolsSubscribeRepository;

    @Override
    @Transactional
    public BaseResponse openPosition(CreateOrderRequest createOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException {
        SyncAPIConnector connector = connectorFactory.getConnector();

        try {

            PositionInfoEntity entity = positionInfoRepository.findPositionInfoEntityByChannelIdAndPriceAndClosedIsFalse(createOrderRequest.getChannelId(), createOrderRequest.getPrice());
            if (entity != null && !entity.getClosed()) {
                log.info("ORDER đã tồn tại");
                throw new BaseException(EnumCodeResponse.ORDER_REJECTED);
            }

            SymbolsSubscribeEntity symbolsSubscribeEntity = symbolsSubscribeRepository.getByChannelIdAndSymbol(createOrderRequest.getChannelId(), createOrderRequest.getSymbols());

            String symbolBroker;
            String symbolSub;
            if (symbolsSubscribeEntity != null) {
                symbolBroker = symbolsSubscribeEntity.getSymbolBroker();
                symbolSub = symbolsSubscribeEntity.getSymbolSubscribe();
            } else {
                symbolBroker = symbolSub = createOrderRequest.getSymbols();
            }

            Long longTimeCurrent = TimeUtils.getLongTime(1);
            TradeTransInfoRecord tradeTransInfoRecord = new TradeTransInfoRecord();
            tradeTransInfoRecord.setCmd(createOrderRequest.getIsBuy() ? TRADE_OPERATION_CODE.BUY : TRADE_OPERATION_CODE.SELL);
            tradeTransInfoRecord.setCustomComment("tradding with api");
            tradeTransInfoRecord.setExpiration(longTimeCurrent);
            tradeTransInfoRecord.setSl(createOrderRequest.getStop());
            SymbolResponse symbol = getSymbol(symbolBroker);
            log.info(symbol.toString());

            ETransactionType eTransactionType;
            if (createOrderRequest.getIsBuy()) {
                tradeTransInfoRecord.setTp(createOrderRequest.getLimit().stream().mapToDouble(value -> value).max().orElseThrow());
                tradeTransInfoRecord.setPrice(symbol.getSymbol().getAsk());
                eTransactionType = ETransactionType.POSITION_BUY;
                createOrderRequest.getLimit().add(createOrderRequest.getPrice());
                createOrderRequest.getLimit().sort(SortUtils::augmentCompare);
            } else {
                createOrderRequest.getLimit().sort(SortUtils::reduceCompare);
                tradeTransInfoRecord.setPrice(symbol.getSymbol().getBid());
                tradeTransInfoRecord.setTp(createOrderRequest.getLimit().stream().mapToDouble(value -> value).min().orElseThrow());
                eTransactionType = ETransactionType.POSITION_SELL;
                createOrderRequest.getLimit().add(createOrderRequest.getPrice());
                createOrderRequest.getLimit().sort(SortUtils::reduceCompare);

            }
            tradeTransInfoRecord.setVolume(0.01);

            tradeTransInfoRecord.setSymbol(symbolBroker);

            tradeTransInfoRecord.setType(TRADE_TRANSACTION_TYPE.OPEN);
            TradeTransactionResponse tradeTransactionResponse = APICommandFactory.executeTradeTransactionCommand(connector, tradeTransInfoRecord);
            log.info("open trade : " + JsonUtils.ObjectToJson(tradeTransactionResponse));
            if (tradeTransactionResponse.getStatus()) {

                Long order = tradeTransactionResponse.getOrder();

                PositionInfoEntity positionInfoEntity = new PositionInfoEntity();
                positionInfoEntity.setOrderId(order);
                positionInfoEntity.setType(eTransactionType);
                positionInfoEntity.setPrice(createOrderRequest.getPrice());
                positionInfoEntity.setBrokerName(BrokerCode.XTB_EXCHANGE);
                positionInfoEntity.setStatus(EStatusTrade.POSITION_PENDING);
                positionInfoEntity.setChannelId(createOrderRequest.getChannelId());
                positionInfoRepository.save(positionInfoEntity);
                try {
                    MetaApiSocketConnect.connection.subscribeToMarketData(symbolSub);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    log.error(JsonUtils.ObjectToJson(ex));
                }

                // insert profit management
                ProfitManagementInfoEntity profitManagementInfo = new ProfitManagementInfoEntity();
                StringBuilder lstProfit = new StringBuilder();
                for (Double aDouble : createOrderRequest.getLimit()) {
                    lstProfit.append(aDouble).append(";");
                }
                profitManagementInfo.setLstProfit(lstProfit.toString());
                profitManagementInfo.setBrokerName(BrokerCode.XTB_EXCHANGE);
                profitManagementInfo.setIsBuy(createOrderRequest.getIsBuy());
                profitManagementInfo.setSymbols(createOrderRequest.getSymbols());
                profitManagementInfo.setOrderId(positionInfoEntity.getOrderId());
                profitManagementInfo.setStatus(EStatusTrade.POSITION_PENDING);
                profitManagementRepository.save(profitManagementInfo);
                return ResponseUtils.created();
            }
        } catch (Exception ex) {
            log.error(JsonUtils.ObjectToJson(ex));
            throw new BaseException(EnumCodeResponse.INTERNAL_SERVER);

        } finally {
            connector.close();
        }

        throw new BaseException(EnumCodeResponse.ORDER_REJECTED);

    }

    @Override
    public BaseResponse modifyPosition(ModifyOrderRequest modifyOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException {
        SyncAPIConnector connector = connectorFactory.getConnector();

        try {
            TradeTransInfoRecord tradeTransInfoRecord = new TradeTransInfoRecord();
            PositionInfoEntity entity;

            if (modifyOrderRequest.getPositionId() != null) {
//                obj.put("customComment", "Modify by job");
                tradeTransInfoRecord.setCustomComment("Modify by job");
                entity = positionInfoRepository.findPositionInfoEntityByPositionId(modifyOrderRequest.getPositionId());
            } else if (modifyOrderRequest.getChannelId() != null && modifyOrderRequest.getPrice() != null && modifyOrderRequest.getPrice() > 0) {
//                obj.put("customComment", "Modify by " + modifyOrderRequest.getChannelId());
                tradeTransInfoRecord.setCustomComment("Modify by " + modifyOrderRequest.getChannelId());
                entity = positionInfoRepository.findPositionInfoEntityByChannelIdAndPriceAndClosedIsFalse(modifyOrderRequest.getChannelId(), modifyOrderRequest.getPrice());
            } else {
                throw new BaseException(EnumCodeResponse.ORDER_NOT_FOUND);
            }
            if (entity == null) {
                throw new BaseException(EnumCodeResponse.ORDER_NOT_FOUND);
            }
            if (modifyOrderRequest.getStopLoss() != null && modifyOrderRequest.getStopLoss() > 0) {
//                obj.put("sl", modifyOrderRequest.getStopLoss());
                tradeTransInfoRecord.setSl(modifyOrderRequest.getStopLoss());
                entity.setStopLoss(modifyOrderRequest.getStopLoss());
            } else
                tradeTransInfoRecord.setSl(entity.getStopLoss());

            if (modifyOrderRequest.getTakeProfit() != null && modifyOrderRequest.getTakeProfit() > 0) {
//                obj.put("tp", modifyOrderRequest.getTakeProfit());
                tradeTransInfoRecord.setSl(modifyOrderRequest.getTakeProfit());
                entity.setTakeProfit(modifyOrderRequest.getTakeProfit());
            } else
                tradeTransInfoRecord.setTp(entity.getTakeProfit());

            if (entity.getType() == ETransactionType.POSITION_BUY)
                tradeTransInfoRecord.setCmd(TRADE_OPERATION_CODE.BUY);
            else
                tradeTransInfoRecord.setCmd(TRADE_OPERATION_CODE.SELL);
            tradeTransInfoRecord.setSymbol(entity.getSymbol());
            Long longTimeCurrent = TimeUtils.getLongTime(1);
            tradeTransInfoRecord.setExpiration(longTimeCurrent);
            tradeTransInfoRecord.setPrice(entity.getOpenPrice());
            tradeTransInfoRecord.setVolume(entity.getVolume());
            tradeTransInfoRecord.setType(TRADE_TRANSACTION_TYPE.MODIFY);
            tradeTransInfoRecord.setOrder(entity.getPositionId());
//            obj.put("order", entity.getPositionId());
//            TradeTransactionCommand tradeTransactionCommand = new TradeTransactionCommand(jsonObject);
//            TradeTransactionResponse tradeTransactionResponse = APICommandFactory.executeTradeTransactionCommand(connector, tradeTransactionCommand);
            TradeTransactionResponse tradeTransactionResponse = APICommandFactory.executeTradeTransactionCommand(connector, tradeTransInfoRecord);
//            TradeTransactionResponse tradeTransactionResponse = APICommandFactory.executeTradeTransactionCommand(connector, tradeTransInfoRecord.getCmd(), TRADE_TRANSACTION_TYPE.MODIFY, null, tradeTransInfoRecord.getSl(), tradeTransInfoRecord.getTp(), null, null, tradeTransInfoRecord.getOrder(), tradeTransInfoRecord.getCustomComment(), tradeTransInfoRecord.getExpiration());
            log.info("modify trade : " + JsonUtils.ObjectToJson(tradeTransactionResponse));
            if (tradeTransactionResponse.getStatus()) {
                positionInfoRepository.save(entity);
                return new BaseResponse(EnumCodeResponse.SUCCESS);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(JsonUtils.ObjectToJson(ex));
            throw new BaseException(EnumCodeResponse.INTERNAL_SERVER);
        } finally {
            connector.close();
        }
        throw new BaseException(EnumCodeResponse.ORDER_REJECTED);
    }

    @Override
    public BaseResponse closePosition(CloseOrderRequest closeOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException {
        return null;
    }

    @Override
    public SymbolResponse getSymbol(String symbol) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException {
        SyncAPIConnector connector = connectorFactory.getConnector();
        return APICommandFactory.executeSymbolCommand(connector, symbol);
    }


}
