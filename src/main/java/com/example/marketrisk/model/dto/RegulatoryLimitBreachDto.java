package com.example.marketrisk.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RegulatoryLimitBreachDto {

    private String symbol;
    private long timestamp;

    private String ruleName;               // e.g. "Basel III Exposure Limit"
    private BigDecimal threshold;
    private BigDecimal actualValue;
    private boolean breached;

}
