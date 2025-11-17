package com.example.marketrisk.repository;

import com.example.marketrisk.model.entity.MarketDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketDataRepository extends JpaRepository<MarketDataEntity, Long> {

    List<MarketDataEntity> findTopBySymbolOrderByTimestampDesc(String symbol);


}
