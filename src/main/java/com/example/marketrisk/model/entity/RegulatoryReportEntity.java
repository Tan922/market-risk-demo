package com.example.marketrisk.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "regulatory_report")
public class RegulatoryReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reportDate;
    private String reportType;    // e.g. DAILY, MONTHLY
    private String content;       // JSON or summary text
    private String name;
    private long timestamp;      // Epoch time of report generation

}
