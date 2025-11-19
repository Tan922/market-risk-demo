package com.example.marketrisk.model;

import lombok.*;
import java.math.BigDecimal;

/**
 * A unified snapshot of market data + risk metrics + AI analysis.
 * This represents the final enriched object flowing into:
 * - Kafka (downstream topics)
 * - AIService (LLM enrichment)
 * - REST API responses
 * - Optional H2 persistence for reporting or Tableau
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MarketRiskSnapshot {

    // ============================================================
    //  Market Data (base signal)
    // ============================================================
    private String symbol;
    private long timestamp;
    private BigDecimal price;
    private BigDecimal volume;

    // ============================================================
    //  Risk Metrics (Kafka Streams pipeline)
    // ============================================================

    /** Profit & Loss on a daily basis */
    private BigDecimal dailyPnl;

    /** Profit & Loss on a monthly basis */
    private BigDecimal monthlyPnl;

    /** 1-day VaR at 95% confidence */
    private BigDecimal var1at95;

    /** 1-day VaR at 99% confidence */
    private BigDecimal var1at99;

    /** 10-day VaR at 95% confidence */
    private BigDecimal var10at95;

    /** 10-day VaR at 99% confidence */
    private BigDecimal var10at99;

    /** 30-day rolling volatility */
    private BigDecimal volatility30;

    /** Regression alpha (CAPM) */
    private BigDecimal alpha;

    /** Regression beta (CAPM) */
    private BigDecimal beta;

    /** Normalized risk score computed by the risk stream */
    private Integer riskScore;

    // ============================================================
    //  AI / LLM Enrichment (AIService)
    // ============================================================

    /** AI sentiment: bullish / bearish / neutral */
    private String aiSentiment;

    /** Multi-sentence summary from the LLM */
    private String aiSummary;

    /** Confidence score from 0–1 */
    private BigDecimal aiConfidenceScore;

    /**
     * Creates a safe snapshot with null defaulting.
     */
    public static MarketRiskSnapshot safe(
            String symbol,
            long timestamp,
            BigDecimal price,
            BigDecimal volume
    ) {
        return MarketRiskSnapshot.builder()
                .symbol(symbol)
                .timestamp(timestamp)
                .price(nz(price))
                .volume(nz(volume))
                .dailyPnl(BigDecimal.ZERO)
                .monthlyPnl(BigDecimal.ZERO)
                .var1at95(BigDecimal.ZERO)
                .var1at99(BigDecimal.ZERO)
                .var10at95(BigDecimal.ZERO)
                .var10at99(BigDecimal.ZERO)
                .volatility30(BigDecimal.ZERO)
                .alpha(BigDecimal.ZERO)
                .beta(BigDecimal.ZERO)
                .riskScore(0)
                .aiSentiment(null)
                .aiSummary(null)
                .aiConfidenceScore(null)
                .build();
    }

    /**
     * Null-safe BigDecimal initializer.
     */
    private static BigDecimal nz(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }

}
