package com.api.orderfx.repository;

import com.api.orderfx.common.EStatusTrade;
import com.api.orderfx.common.ETransactionType;
import com.api.orderfx.entity.ProfitManagementInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfitManagementRepository extends JpaRepository<ProfitManagementInfoEntity, Long> {

    List<ProfitManagementInfoEntity> getAllBySymbolsAndClosedIsFalse(String symbol);
    List<ProfitManagementInfoEntity> getAllBySymbolsAndStatusAndClosedIsFalse(String symbol, EStatusTrade status);
    ProfitManagementInfoEntity getProfitManagementInfoEntitiesByPositionId(Long positionId);
    ProfitManagementInfoEntity getProfitManagementInfoEntitiesByOrderId(Long orderId);

}
