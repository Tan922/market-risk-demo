package com.example.marketrisk.model.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrichedMarketDataDto {
    private String symbol;
    private BigDecimal price;
    private BigDecimal volume;
    private BigDecimal notional;
    private BigDecimal riskScore;
    private long timestamp;
}
