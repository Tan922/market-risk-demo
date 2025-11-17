package com.example.marketrisk.repository;

import com.example.marketrisk.model.entity.EnrichedMarketDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrichedMarketDataRepository extends JpaRepository<EnrichedMarketDataEntity, Long> {

    List<EnrichedMarketDataEntity> findBySymbolOrderByTimestampDesc(String symbol);

    List<EnrichedMarketDataEntity> findTop100BySymbolOrderByTimestampDesc(String symbol);

}
