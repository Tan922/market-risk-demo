package com.example.marketrisk.model.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskMetricsDto {
    private String symbol;
    private long timestamp;
    private BigDecimal var99;
    private BigDecimal var95;
    private BigDecimal volatility;
    private BigDecimal totalExposure;
    private BigDecimal totalRiskScore;
}
