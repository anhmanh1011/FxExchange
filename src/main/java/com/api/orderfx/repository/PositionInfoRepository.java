package com.api.orderfx.repository;

import com.api.orderfx.common.EStatusTrade;
import com.api.orderfx.entity.PositionInfoEntity;
import com.api.orderfx.entity.ProfitManagementInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionInfoRepository extends JpaRepository<PositionInfoEntity, Long> {
    PositionInfoEntity findPositionInfoEntityByChannelIdAndPriceAndClosedIsFalse(Long channelId,Double price);
    PositionInfoEntity findPositionInfoEntityByPositionId(Long positionId);
    PositionInfoEntity findPositionInfoEntityByOrderId(Long orderId);
    List<PositionInfoEntity> getAllBySymbolAndStatusAndClosedIsFalse(String symbol, EStatusTrade status);

}
