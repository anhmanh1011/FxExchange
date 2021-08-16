package com.api.orderfx.Utils;

import api.message.records.TradeRecord;
import com.api.orderfx.entity.PositionInfoEntity;
import lombok.experimental.UtilityClass;

import java.sql.Timestamp;

@UtilityClass
public class ConverterUtils {

    public PositionInfoEntity tradeRecordInfoToPositionInfoEntity(TradeRecord tradeRecord) {

        PositionInfoEntity tradeTransInfoEntity = new PositionInfoEntity();
        tradeTransInfoEntity.setClosed(tradeRecord.isClosed());
        tradeTransInfoEntity.setClosePrice(tradeRecord.getClose_price());
        tradeTransInfoEntity.setComment(tradeRecord.getComment());
        tradeTransInfoEntity.setCommission(tradeRecord.getCommission());
        tradeTransInfoEntity.setOpenTime(new Timestamp(tradeRecord.getOpen_time()));
        tradeTransInfoEntity.setOpenPrice(tradeRecord.getOpen_price());
        tradeTransInfoEntity.setPositionId(tradeRecord.getPosition());
        tradeTransInfoEntity.setProfit(tradeRecord.getProfit());
        tradeTransInfoEntity.setStopLoss(tradeRecord.getSl());
        tradeTransInfoEntity.setSymbol(tradeRecord.getSymbol());
        tradeTransInfoEntity.setTakeProfit(tradeRecord.getTp());
        tradeTransInfoEntity.setVolume(tradeRecord.getVolume());

        return tradeTransInfoEntity;
    }



}
