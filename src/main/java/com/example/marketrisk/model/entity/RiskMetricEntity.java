package com.example.marketrisk.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "risk_metric")
public class RiskMetricEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long timestamp;
    private String symbol;

    private BigDecimal pnl;
    private BigDecimal var95;
    private BigDecimal var99;
    private BigDecimal exposure;
    private BigDecimal position;

}
