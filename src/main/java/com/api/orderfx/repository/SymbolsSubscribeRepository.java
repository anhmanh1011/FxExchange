package com.api.orderfx.repository;

import com.api.orderfx.entity.SymbolsSubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymbolsSubscribeRepository extends JpaRepository<SymbolsSubscribeEntity, Long> {
    SymbolsSubscribeEntity getByChannelIdAndSymbol(Long channelId, String symbol);
}
