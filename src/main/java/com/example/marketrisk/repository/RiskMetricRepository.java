package com.example.marketrisk.repository;

import com.example.marketrisk.model.entity.RiskMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskMetricRepository extends JpaRepository<RiskMetricEntity, Long> {

    List<RiskMetricEntity> findBySymbolOrderByTimestampDesc(String symbol);

}
