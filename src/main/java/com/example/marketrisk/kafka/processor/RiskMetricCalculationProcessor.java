package com.example.marketrisk.kafka.processor;

import com.example.marketrisk.model.EnrichedMarketData;
import com.example.marketrisk.model.RiskMetric;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RiskMetricCalculationProcessor {

    public RiskMetric computeRisk(EnrichedMarketData data) {
        RiskMetric rm = new RiskMetric();
        rm.setSymbol(data.getSymbol());
        rm.setTimestamp(data.getTimestamp());
        rm.setPnl(data.getPriceChange());
        rm.setExposure(data.getNotional());
        rm.setPosition(data.getVolume());
        // Placeholder calculations for VaR
        rm.setVar95(data.getNotional().multiply(BigDecimal.valueOf(0.05)));
        rm.setVar99(data.getNotional().multiply(BigDecimal.valueOf(0.01)));
        return rm;
    }
}
