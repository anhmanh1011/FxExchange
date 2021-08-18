package com.api.orderfx.ApplicationListener;

import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import cloud.metaapi.sdk.clients.meta_api.SynchronizationListener;
import cloud.metaapi.sdk.clients.meta_api.models.*;
import cloud.metaapi.sdk.meta_api.MetaApiConnection;
import cloud.metaapi.sdk.util.JsonMapper;
import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.common.BaseException;
import com.api.orderfx.common.EStatusTrade;
import com.api.orderfx.config.SpringContext;
import com.api.orderfx.entity.PositionInfoEntity;
import com.api.orderfx.entity.ProfitManagementInfoEntity;
import com.api.orderfx.model.common.BaseResponse;
import com.api.orderfx.model.fxcm.request.ModifyOrderRequest;
import com.api.orderfx.repository.PositionInfoRepository;
import com.api.orderfx.repository.ProfitManagementRepository;
import com.api.orderfx.service.ITradeApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
public class SynchronizationImpl extends SynchronizationListener {
    public MetaApiConnection connection = null;
    ProfitManagementRepository profitManagementRepository;
    PositionInfoRepository positionInfoRepository;


    SynchronizationImpl(MetaApiConnection connection) {
        this.connection = connection;
        profitManagementRepository = SpringContext.getBean(ProfitManagementRepository.class);
        positionInfoRepository = SpringContext.getBean(PositionInfoRepository.class);
    }


    @Override
    public CompletableFuture<Void> onAccountInformationUpdated(String instanceIndex, MetatraderAccountInformation accountInformation) {
        try {
            System.out.println("  accountInformation " + asJson(accountInformation));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> onSymbolPriceUpdated(String instanceIndex, MetatraderSymbolPrice price) {
        try {
            String symbol = price.symbol;
            System.out.println(symbol + " price updated " + asJson(price));
            log.info(symbol + " price updated " + asJson(price));
            List<ProfitManagementInfoEntity> profits = profitManagementRepository.getAllBySymbolsAndStatusAndClosedIsFalse(symbol, EStatusTrade.POSITION_SUCCESS);
            List<PositionInfoEntity> positionInfoEntities = positionInfoRepository.getAllBySymbolAndStatusAndClosedIsFalse(symbol, EStatusTrade.POSITION_SUCCESS);

            profits.parallelStream().forEach(profitManagementInfoEntity -> {

                double[] v = Arrays.stream(profitManagementInfoEntity.getLstProfit().split(";")).mapToDouble(Double::parseDouble).toArray();
                Double priceModify = null;
                for (int i = 1; i < v.length; i++) {
                    if (profitManagementInfoEntity.getIsBuy()) {
                        if (v[i] < price.ask) {
                            priceModify = v[i - 1];
                            break;
                        }
                    } else {
                        if (v[i] > price.bid) {
                            priceModify = v[i - 1];
                            break;
                        }
                    }
                }
                PositionInfoEntity entity = positionInfoEntities.stream().filter(positionInfoEntity -> positionInfoEntity.getPositionId().equals(profitManagementInfoEntity.getPositionId())).findFirst().get();
                Double stopLoss = entity.getStopLoss();
                if (priceModify != null && !priceModify.equals(stopLoss)) {
                    ITradeApi bean = (ITradeApi) SpringContext.getBean(entity.getBrokerName());
                    try {
                        ModifyOrderRequest modifyOrderRequest = new ModifyOrderRequest();
                        modifyOrderRequest.setPositionId(entity.getPositionId());
                        modifyOrderRequest.setStopLoss(priceModify);
                        System.out.println("update stoploss to " + priceModify);
                        log.info(entity.getSymbol() + "update stoploss to " + priceModify);
                        bean.modifyPosition(modifyOrderRequest);
                    } catch (APIErrorResponse apiErrorResponse) {
                        apiErrorResponse.printStackTrace();
                    } catch (APIReplyParseException e) {
                        e.printStackTrace();
                        log.error(JsonUtils.ObjectToJson(e));
                    } catch (APICommunicationException e) {
                        e.printStackTrace();
                        log.error(JsonUtils.ObjectToJson(e));
                    } catch (APICommandConstructionException e) {
                        e.printStackTrace();
                        log.error(JsonUtils.ObjectToJson(e));
                    } catch (BaseException e) {
                        e.printStackTrace();
                        log.error(JsonUtils.ObjectToJson(e));
                    }
                }
            });


        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(JsonUtils.ObjectToJson(e));
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> onPositionUpdated(String instanceIndex, MetatraderPosition position) {
        log.info("onPositionUpdated: " + JsonUtils.ObjectToJson(position));
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> onPositionRemoved(String instanceIndex, String positionId) {
        CompletableFuture<MetatraderDeals> dealsByPosition = connection.getDealsByPosition(positionId);
        try {
            MetatraderDeals metatraderDeals = dealsByPosition.get();
            MetatraderDeal deal = metatraderDeals.getDeals().stream().filter(metatraderDeal -> metatraderDeal.getEntryType().equals(MetatraderDeal.DealEntryType.DEAL_ENTRY_OUT)).findFirst().get();
            PositionInfoEntity entity = positionInfoRepository.findPositionInfoEntityByPositionId(Long.valueOf(deal.getPositionId()));
            entity.setProfit(deal.getProfit());
            entity.setCloseTime(new Timestamp(new Date().getTime()));
            entity.setClosePrice(deal.getPrice());
            entity.setClosed(true);
            entity.setStatus(EStatusTrade.POSITION_CLOSED);
            positionInfoRepository.save(entity);
            ProfitManagementInfoEntity positionInfoEntity = profitManagementRepository.getProfitManagementInfoEntitiesByOrderId(Long.valueOf(deal.getPositionId()));
            positionInfoEntity.setClosed(true);
            positionInfoEntity.setStatus(EStatusTrade.POSITION_CLOSED);
            profitManagementRepository.save(positionInfoEntity);

            log.info("onPositionRemoved: " + asJson(metatraderDeals));
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(JsonUtils.ObjectToJson(e));
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error(JsonUtils.ObjectToJson(e));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(JsonUtils.ObjectToJson(e));
        }
        return CompletableFuture.completedFuture(null);
    }


    private static String asJson(Object object) throws JsonProcessingException {
        return JsonMapper.getInstance().writeValueAsString(object);
    }
}
