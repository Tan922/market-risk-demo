package com.example.marketrisk.model;

import com.example.marketrisk.model.entity.RiskMetricEntity;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class RiskMetric {

    private String symbol;
    private long timestamp;

    private BigDecimal pnl;        // Profit & Loss
    private BigDecimal var95;      // Value at Risk (95%)
    private BigDecimal var99;      // Value at Risk (99%)
    private BigDecimal exposure;
    private BigDecimal position;

    public RiskMetricEntity toEntity() {
        RiskMetricEntity entity = new RiskMetricEntity();
        entity.setSymbol(this.getSymbol());
        entity.setTimestamp(this.getTimestamp());
        entity.setPnl(this.getPnl());
        entity.setVar95(this.getVar95());
        entity.setVar99(this.getVar99());
        entity.setExposure(this.getExposure());
        entity.setPosition(this.getPosition());
        return entity;
    }

}
