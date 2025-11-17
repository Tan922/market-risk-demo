package com.example.marketrisk.service;

import com.example.marketrisk.model.EnrichedMarketData;
import com.example.marketrisk.model.MarketData;
import com.example.marketrisk.repository.EnrichedMarketDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EnrichmentService {

    private final EnrichedMarketDataRepository repository;

    public EnrichedMarketData enrich(MarketData md) {
        BigDecimal notional = md.getPrice().multiply(md.getVolume());
        BigDecimal riskScore = md.getPrice().multiply(BigDecimal.valueOf(0.02)); // placeholder

        EnrichedMarketData e = new EnrichedMarketData();
        e.setSymbol(md.getSymbol());
        e.setPrice(md.getPrice());
        e.setVolume(md.getVolume());
        e.setTimestamp(md.getTimestamp());
        e.setNotional(notional);
        e.setRiskScore(riskScore);

        return e;
    }

    public EnrichedMarketData enrichAndSave(MarketData md) {
        EnrichedMarketData e = enrich(md);
        repository.save(e.toEntity());
        return e;
    }
}
