package com.example.marketrisk.kafka.processor;

import com.example.marketrisk.model.EnrichedMarketData;
import com.example.marketrisk.model.MarketData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EnrichProcessor {

    public EnrichedMarketData enrich(MarketData data) {
        BigDecimal notional = data.getPrice().multiply(data.getVolume());
        BigDecimal riskScore = data.getPrice().multiply(BigDecimal.valueOf(0.02)); // placeholder

        EnrichedMarketData enriched = new EnrichedMarketData();
        enriched.setSymbol(data.getSymbol());
        enriched.setPrice(data.getPrice());
        enriched.setVolume(data.getVolume());
        enriched.setTimestamp(data.getTimestamp());
        enriched.setNotional(notional);
        enriched.setRiskScore(riskScore);
        return enriched;
    }
}
