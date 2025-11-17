package com.example.marketrisk.model.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegulatoryReportDto {
    private String reportId;
    private String reportType;     // e.g. "BASEL_III", "FRTB", "LCR"
    private long generationTimestamp;

    private BigDecimal totalExposure;
    private BigDecimal totalVaR99;
    private BigDecimal totalVaR95;

    private List<SymbolRiskSection> symbols;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SymbolRiskSection {
        private String symbol;
        private BigDecimal exposure;
        private BigDecimal var99;
        private BigDecimal var95;

        // getters & setters
    }
}
