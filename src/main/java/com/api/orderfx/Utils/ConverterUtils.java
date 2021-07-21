package com.api.orderfx.Utils;

import api.message.records.TradeRecord;
import com.api.orderfx.entity.TradeTransInfoEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConverterUtils {

    public TradeTransInfoEntity tradeRecordInfoToTradeTransInfoEntity(TradeRecord tradeRecord) {

        TradeTransInfoEntity tradeTransInfoEntity = new TradeTransInfoEntity();
        tradeTransInfoEntity.setClosed(tradeRecord.isClosed());
        tradeTransInfoEntity.setClose_price(tradeRecord.getClose_price());
        tradeTransInfoEntity.setClose_time(tradeRecord.getClose_time());
        tradeTransInfoEntity.setCmd(tradeRecord.getCmd());
        tradeTransInfoEntity.setComment(tradeRecord.getComment());
        tradeTransInfoEntity.setCommission(tradeRecord.getCommission());
        tradeTransInfoEntity.setCustomComment(tradeRecord.getCustomComment());
        tradeTransInfoEntity.setDigits(tradeRecord.getDigits());
        tradeTransInfoEntity.setExpiration(tradeRecord.getExpiration());
        tradeTransInfoEntity.setMargin_rate(tradeRecord.getMargin_rate());
        tradeTransInfoEntity.setOpen_time(tradeRecord.getOpen_time());
        tradeTransInfoEntity.setOpen_price(tradeRecord.getOpen_price());
        tradeTransInfoEntity.setOrder(tradeRecord.getOrder());
        tradeTransInfoEntity.setOrder2(tradeRecord.getOrder2());
        tradeTransInfoEntity.setPosition(tradeRecord.getPosition());
        tradeTransInfoEntity.setProfit(tradeRecord.getProfit());
        tradeTransInfoEntity.setSl(tradeRecord.getSl());
        tradeTransInfoEntity.setStorage(tradeRecord.getStorage());
        tradeTransInfoEntity.setSymbol(tradeRecord.getSymbol());
        tradeTransInfoEntity.setTp(tradeRecord.getTp());
        tradeTransInfoEntity.setVolume(tradeRecord.getVolume());

        return tradeTransInfoEntity;
    }

}
