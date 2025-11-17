package com.example.marketrisk.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "market_data")
@Inheritance(strategy = InheritanceType.JOINED)
public class MarketDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long timestamp;      // unique tick timestamp
    private String symbol;
    private BigDecimal price;
    private BigDecimal volume;
    private BigDecimal notional;
    private BigDecimal riskScore;

}
