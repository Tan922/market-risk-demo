package com.example.marketrisk.model;

import com.example.marketrisk.model.entity.MarketDataEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MarketData {

    private String symbol;
    private BigDecimal price;
    private BigDecimal volume;
    private long timestamp;

    public MarketDataEntity toEntity() {
        MarketDataEntity entity = new MarketDataEntity();
        entity.setSymbol(this.symbol);
        entity.setPrice(this.price);
        entity.setVolume(this.volume);
        entity.setTimestamp(this.timestamp);
        return entity;
    }

}
