package com.example.marketrisk.model;

import com.example.marketrisk.model.entity.RegulatoryReportEntity;

import java.time.LocalDate;

public class RegulatoryReport {
    private Long id;
    private LocalDate reportDate;
    private String reportType;    // e.g. DAILY, MONTHLY
    private String content;       // JSON or summary text
    private String name;

    public RegulatoryReportEntity toEntity() {
        RegulatoryReportEntity entity = new RegulatoryReportEntity();
        entity.setId(this.id);
        entity.setReportDate(this.reportDate);
        entity.setReportType(this.reportType);
        entity.setContent(this.content);
        entity.setName(this.name);
        return entity;
    }
}
