package com.api.orderfx.service.meta_api;

import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.SymbolResponse;
import cloud.metaapi.sdk.clients.meta_api.models.MarketTradeOptions;
import cloud.metaapi.sdk.clients.meta_api.models.MetatraderPosition;
import cloud.metaapi.sdk.clients.meta_api.models.MetatraderTradeResponse;
import cloud.metaapi.sdk.meta_api.MetaApiConnection;
import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.Utils.SortUtils;
import com.api.orderfx.common.*;
import com.api.orderfx.entity.PositionInfoEntity;
import com.api.orderfx.entity.ProfitManagementInfoEntity;
import com.api.orderfx.entity.SymbolsSubscribeEntity;
import com.api.orderfx.mapper.PositionMapper;
import com.api.orderfx.model.common.BaseResponse;
import com.api.orderfx.model.fxcm.request.CloseOrderRequest;
import com.api.orderfx.model.fxcm.request.CreateOrderRequest;
import com.api.orderfx.model.fxcm.request.ModifyOrderRequest;
import com.api.orderfx.repository.PositionInfoRepository;
import com.api.orderfx.repository.ProfitManagementRepository;
import com.api.orderfx.repository.SymbolsSubscribeRepository;
import com.api.orderfx.service.ITradeApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service(BrokerCode.BDS_EXCHANGE)
@Log4j2
public class BdsApiImpl implements ITradeApi {

    @Autowired
    PositionInfoRepository positionInfoRepository;

    @Autowired
    PositionMapper positionMapper;


    @Autowired
    ProfitManagementRepository profitManagementRepository;


    @Autowired
    SymbolsSubscribeRepository symbolsSubscribeRepository;
    @Autowired
    MetaApiConnectorFactory metaApiConnectorFactory;

    @Override
    @Transactional
    public BaseResponse openPosition(CreateOrderRequest createOrderRequest) throws BaseException {

        ETransactionType eTransactionType;
        SymbolsSubscribeEntity symbolsSubscribeEntity = symbolsSubscribeRepository.getByChannelIdAndSymbol(createOrderRequest.getChannelId(), createOrderRequest.getSymbols());

        String symbolBroker;
        if (symbolsSubscribeEntity != null) {
            symbolBroker = symbolsSubscribeEntity.getSymbolBroker();
        } else {
            symbolBroker = createOrderRequest.getSymbols();
        }
        MetaApiConnection connection = null;
        try {
            connection = metaApiConnectorFactory.getConnection();

            MetatraderTradeResponse metatraderTradeResponse;

            if (createOrderRequest.getIsBuy()) {


                Double tp = createOrderRequest.getLimit().stream().mapToDouble(value -> value).max().orElseThrow();
                metatraderTradeResponse = connection.createMarketBuyOrder(symbolBroker, 0.01, createOrderRequest.getStop(), tp, new MarketTradeOptions() {{
                    comment = "BUY" + "_" + createOrderRequest.getBroker() + "_" + createOrderRequest.getSymbols();
                }}).get();
                eTransactionType = ETransactionType.POSITION_BUY;
                createOrderRequest.getLimit().add(createOrderRequest.getPrice());
                createOrderRequest.getLimit().sort(SortUtils::augmentCompare);

            } else {
                Double tp = createOrderRequest.getLimit().stream().mapToDouble(value -> value).min().orElseThrow();
                metatraderTradeResponse = connection.createMarketSellOrder(symbolBroker, 0.01, createOrderRequest.getStop(), tp, new MarketTradeOptions() {{
                    comment = "SELL" + "_" + createOrderRequest.getBroker() + "_" + createOrderRequest.getSymbols();
                }}).get();
                eTransactionType = ETransactionType.POSITION_SELL;
                createOrderRequest.getLimit().add(createOrderRequest.getPrice());
                createOrderRequest.getLimit().sort(SortUtils::reduceCompare);

            }
            if (metatraderTradeResponse.getPositionId() != null) {
                CompletableFuture<MetatraderPosition> position = connection.getPosition(metatraderTradeResponse.getPositionId());
                MetatraderPosition metatraderPosition = position.get();
                PositionInfoEntity positionInfoEntity = positionMapper.MetaTraderPositionToPositionInfoEntity(metatraderPosition);
                positionInfoEntity.setType(eTransactionType);
                positionInfoEntity.setPrice(createOrderRequest.getPrice());
                positionInfoEntity.setBrokerName(BrokerCode.BDS_EXCHANGE);
                positionInfoEntity.setStatus(EStatusTrade.POSITION_SUCCESS);
                positionInfoEntity.setChannelId(createOrderRequest.getChannelId());
                positionInfoRepository.save(positionInfoEntity);

                ProfitManagementInfoEntity profitManagementInfo = new ProfitManagementInfoEntity();

                StringBuilder lstProfit = new StringBuilder();
                for (Double aDouble : createOrderRequest.getLimit()) {
                    lstProfit.append(aDouble).append(";");
                }
                profitManagementInfo.setLstProfit(lstProfit.toString());
                profitManagementInfo.setBrokerName(BrokerCode.BDS_EXCHANGE);
                profitManagementInfo.setIsBuy(createOrderRequest.getIsBuy());
                profitManagementInfo.setSymbols(symbolBroker);
                profitManagementInfo.setOrderId(positionInfoEntity.getOrderId());
                profitManagementInfo.setPositionId(positionInfoEntity.getOrderId());
                profitManagementInfo.setStatus(EStatusTrade.POSITION_SUCCESS);
                profitManagementRepository.save(profitManagementInfo);

                return ResponseUtils.created();
            }
            throw new BaseException(EnumCodeResponse.ORDER_REJECTED);

        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(JsonUtils.ObjectToJson(e));
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error(JsonUtils.ObjectToJson(e));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.close();
        }
        throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @Override
    public BaseResponse modifyPosition(ModifyOrderRequest modifyOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException {
        MetaApiConnection connection = null;
        try {
            PositionInfoEntity entity;
            connection = metaApiConnectorFactory.getConnection();
            if (modifyOrderRequest.getPositionId() != null) {
                entity = positionInfoRepository.findPositionInfoEntityByPositionId(modifyOrderRequest.getPositionId());
            } else if (modifyOrderRequest.getChannelId() != null && modifyOrderRequest.getPrice() != null && modifyOrderRequest.getPrice() > 0) {
                entity = positionInfoRepository.findPositionInfoEntityByChannelIdAndPriceAndClosedIsFalse(modifyOrderRequest.getChannelId(), modifyOrderRequest.getPrice());
            } else {
                throw new BaseException(EnumCodeResponse.ORDER_NOT_FOUND);
            }
            if (entity == null) {
                throw new BaseException(EnumCodeResponse.ORDER_NOT_FOUND);
            }
            if (modifyOrderRequest.getStopLoss() != null && modifyOrderRequest.getStopLoss() > 0) {
                entity.setStopLoss(modifyOrderRequest.getStopLoss());

            }
            if (modifyOrderRequest.getTakeProfit() != null && modifyOrderRequest.getTakeProfit() > 0) {
                entity.setTakeProfit(modifyOrderRequest.getTakeProfit());
            }

            CompletableFuture<MetatraderTradeResponse> modifyPosition = connection.modifyPosition(entity.getPositionId().toString(), entity.getStopLoss(), entity.getTakeProfit());
            MetatraderTradeResponse tradeResponse = modifyPosition.get();
            log.info("modify trade : " + JsonUtils.ObjectToJson(tradeResponse));
            positionInfoRepository.save(entity);
            return new BaseResponse(EnumCodeResponse.SUCCESS);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(JsonUtils.ObjectToJson(ex));
            throw new BaseException(EnumCodeResponse.INTERNAL_SERVER);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public BaseResponse closePosition(CloseOrderRequest closeOrderRequest) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException {
        MetaApiConnection connection = null;
        try {
            PositionInfoEntity entity;
            connection = metaApiConnectorFactory.getConnection();
            if (closeOrderRequest.getPositionId() != null) {
                entity = positionInfoRepository.findPositionInfoEntityByPositionId(closeOrderRequest.getPositionId());
            } else if (closeOrderRequest.getChannelId() != null && closeOrderRequest.getPrice() != null && closeOrderRequest.getPrice() > 0) {
                entity = positionInfoRepository.findPositionInfoEntityByChannelIdAndPriceAndClosedIsFalse(closeOrderRequest.getChannelId(), closeOrderRequest.getPrice());
            } else {
                throw new BaseException(EnumCodeResponse.ORDER_NOT_FOUND);
            }
            if (entity == null) {
                throw new BaseException(EnumCodeResponse.ORDER_NOT_FOUND);
            }

            CompletableFuture<MetatraderTradeResponse> closePosition = connection.closePosition(entity.getPositionId().toString(), new MarketTradeOptions());
            MetatraderTradeResponse tradeResponse = closePosition.get();
            log.info("modify trade : " + JsonUtils.ObjectToJson(tradeResponse));
            entity.setClosed(true);
            entity.setStatus(EStatusTrade.POSITION_CLOSED);
            positionInfoRepository.save(entity);

            ProfitManagementInfoEntity profitManagementInfoEntity = profitManagementRepository.getProfitManagementInfoEntitiesByOrderId(entity.getOrderId());
            profitManagementInfoEntity.setClosed(true);
            profitManagementInfoEntity.setStatus(EStatusTrade.POSITION_CLOSED);
            profitManagementRepository.save(profitManagementInfoEntity);
            return new BaseResponse(EnumCodeResponse.SUCCESS);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(JsonUtils.ObjectToJson(ex));
            throw new BaseException(EnumCodeResponse.INTERNAL_SERVER);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public SymbolResponse getSymbol(String symbol) throws APIErrorResponse, APIReplyParseException, APICommunicationException, APICommandConstructionException, BaseException {
        return null;
    }
}
