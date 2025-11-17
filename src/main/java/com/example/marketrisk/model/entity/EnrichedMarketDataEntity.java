package com.example.marketrisk.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "enriched_market_data")
public class EnrichedMarketDataEntity extends MarketDataEntity {

    private BigDecimal movingAverage;
    private BigDecimal volatility;
    private BigDecimal priceChange;
    private BigDecimal percentageChange;

}
