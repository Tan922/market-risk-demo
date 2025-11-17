package com.example.marketrisk.model;

import com.example.marketrisk.model.entity.EnrichedMarketDataEntity;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class EnrichedMarketData extends MarketData{
    private BigDecimal movingAverage;
    private BigDecimal volatility;
    private BigDecimal priceChange;
    private BigDecimal percentageChange;
    private BigDecimal notional;
    private BigDecimal riskScore;

    public EnrichedMarketDataEntity toEntity() {
        EnrichedMarketDataEntity entity = new EnrichedMarketDataEntity();
        entity.setSymbol(this.getSymbol());
        entity.setPrice(this.getPrice());
        entity.setVolume(this.getVolume());
        entity.setTimestamp(this.getTimestamp());
        entity.setMovingAverage(this.getMovingAverage());
        entity.setVolatility(this.getVolatility());
        entity.setPriceChange(this.getPriceChange());
        entity.setPercentageChange(this.getPercentageChange());
        entity.setNotional(this.getNotional());
        entity.setRiskScore(this.getRiskScore());
        return entity;
    }

}
