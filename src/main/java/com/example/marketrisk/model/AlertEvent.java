package com.example.marketrisk.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertEvent {

    private String symbol;
    private String alertType;  // e.g. "VOLATILITY_SPIKE", "PRICE_DROP"
    private String message;
    private long timestamp;

}
