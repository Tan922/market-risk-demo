package com.example.marketrisk.model;

import com.example.marketrisk.model.dto.MarketDataRequest;
import com.example.marketrisk.model.dto.MarketDataResponse;
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
}
