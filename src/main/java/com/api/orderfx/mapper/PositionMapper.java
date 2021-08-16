package com.api.orderfx.mapper;

import api.message.records.STradeRecord;
import api.message.records.TradeRecord;
import cloud.metaapi.sdk.clients.meta_api.models.MetatraderPosition;
import com.api.orderfx.entity.PositionInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
@Component
public interface PositionMapper {

    @Mappings({
            @Mapping(target = "positionId", source = "position"),
            @Mapping(target = "closed", source = "closed"),
            @Mapping(target = "closePrice", source = "close_price"),
            @Mapping(target = "closeTime", source = "close_time", qualifiedByName = "millisecondsToTimestamp"),
            @Mapping(target = "comment", source = "comment"),
            @Mapping(target = "commission", source = "commission"),
            @Mapping(target = "openTime", source = "open_time", qualifiedByName = "millisecondsToTimestamp"),
            @Mapping(target = "openPrice", source = "open_price"),
            @Mapping(target = "profit", source = "profit"),
            @Mapping(target = "stopLoss", source = "sl"),
            @Mapping(target = "takeProfit", source = "tp"),
            @Mapping(target = "symbol", source = "symbol"),
            @Mapping(target = "volume", source = "volume"),
            @Mapping(target = "type", ignore = true),
            @Mapping(target = "status", ignore = true),
    })
    PositionInfoEntity sTradeRecordXTBToPositionInfoEntity(STradeRecord tradeRecord);

  @Mappings({
            @Mapping(target = "positionId", source = "position"),
            @Mapping(target = "closed", source = "closed"),
            @Mapping(target = "closePrice", source = "close_price"),
            @Mapping(target = "closeTime", source = "close_time", qualifiedByName = "millisecondsToTimestamp"),
            @Mapping(target = "comment", source = "comment"),
            @Mapping(target = "commission", source = "commission"),
            @Mapping(target = "openTime", source = "open_time", qualifiedByName = "millisecondsToTimestamp"),
            @Mapping(target = "openPrice", source = "open_price"),
            @Mapping(target = "profit", source = "profit"),
            @Mapping(target = "stopLoss", source = "sl"),
            @Mapping(target = "takeProfit", source = "tp"),
            @Mapping(target = "symbol", source = "symbol"),
            @Mapping(target = "volume", source = "volume"),
            @Mapping(target = "type", ignore = true),
            @Mapping(target = "status", ignore = true),
    })
    PositionInfoEntity tradeRecordXTBToPositionInfoEntity(TradeRecord tradeRecord);


    @Mappings({
            @Mapping(target = "positionId", source = "id"),
            @Mapping(target = "closed", ignore = true),
            @Mapping(target = "closePrice", ignore = true),
            @Mapping(target = "closeTime", ignore = true),
            @Mapping(target = "comment", ignore = true),
            @Mapping(target = "commission", source = "commission"),
            @Mapping(target = "openTime", ignore = true),
            @Mapping(target = "openPrice", source = "openPrice"),
            @Mapping(target = "profit", source = "profit"),
            @Mapping(target = "stopLoss", source = "stopLoss"),
            @Mapping(target = "takeProfit", source = "takeProfit"),
            @Mapping(target = "symbol", source = "symbol"),
            @Mapping(target = "volume", source = "volume"),
            @Mapping(target = "orderId", source = "id"),
            @Mapping(target = "type", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "status", ignore = true),
    })
    PositionInfoEntity MetaTraderPositionToPositionInfoEntity(MetatraderPosition metatraderPosition);




    @Named("millisecondsToTimestamp")
    default Timestamp millisecondsToTimestamp(Long milliseconds) {
        if (milliseconds != null)
            return new Timestamp(milliseconds);
        return null;
    }

}
