package com.example.marketrisk.model;

import com.example.marketrisk.model.dto.MarketDataRequest;
import com.example.marketrisk.model.dto.MarketDataResponse;
import com.example.marketrisk.model.entity.MarketDataEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class MarketData {

    private String symbol;
    private BigDecimal price;
    private BigDecimal volume;
    private long timestamp;

    public static MarketData fromEntity(MarketDataEntity entity) {
        MarketData data = new MarketData();
        data.setSymbol(entity.getSymbol());
        data.setPrice(entity.getPrice());
        data.setVolume(entity.getVolume());
        data.setTimestamp(entity.getTimestamp());
        return data;
    }

    public static MarketData fromRequest(MarketDataRequest request) {
        MarketData data = new MarketData();
        data.setSymbol(request.getSymbol());
        data.setPrice(request.getPrice());
        data.setVolume(request.getVolume());
        data.setTimestamp(Instant.now().toEpochMilli());
        return data;
    }

    public MarketDataResponse toResponse() {
        MarketDataResponse response = new MarketDataResponse();
        response.setSymbol(this.symbol);
        response.setPrice(this.price);
        response.setVolume(this.volume);
        response.setTimestamp(this.timestamp);
        return response;
    }

    public MarketDataEntity toEntity() {
        MarketDataEntity entity = new MarketDataEntity();
        entity.setSymbol(this.symbol);
        entity.setPrice(this.price);
        entity.setVolume(this.volume);
        entity.setTimestamp(this.timestamp);
        return entity;
    }

}
