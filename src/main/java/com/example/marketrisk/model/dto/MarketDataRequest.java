package com.example.marketrisk.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketDataRequest {
    private String symbol;
    private BigDecimal price;
    private BigDecimal volume;
}
