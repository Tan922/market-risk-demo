package com.example.marketrisk.service;

import com.example.marketrisk.model.dto.RiskMetricsDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class RiskService {

    public RiskMetricsDto calculateRisk(String symbol) {

        // TODO: Replace with real retrieval from DB / Kafka / Streams state store
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal exposure = BigDecimal.valueOf(500000);

        RiskMetricsDto dto = new RiskMetricsDto();
        dto.setSymbol(symbol);
        dto.setTimestamp(System.currentTimeMillis());

        // Dummy placeholder logic
        dto.setVar99(price.multiply(BigDecimal.valueOf(0.03)).setScale(4, RoundingMode.HALF_UP));
        dto.setVar95(price.multiply(BigDecimal.valueOf(0.02)).setScale(4, RoundingMode.HALF_UP));
        dto.setVolatility(BigDecimal.valueOf(0.15));
        dto.setTotalExposure(exposure);
        dto.setTotalRiskScore(exposure.multiply(BigDecimal.valueOf(0.00042)).setScale(4, RoundingMode.HALF_UP));

        return dto;
    }
}
