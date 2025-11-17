package com.example.marketrisk.service;

import com.example.marketrisk.model.dto.RegulatoryLimitBreachDto;
import com.example.marketrisk.model.dto.RegulatoryReportDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class ReportingService {

    public RegulatoryReportDto generateReport(String reportType) {

        RegulatoryReportDto dto = new RegulatoryReportDto();
        dto.setReportId("RPT-" + System.currentTimeMillis());
        dto.setReportType(reportType);
        dto.setGenerationTimestamp(System.currentTimeMillis());

        dto.setTotalExposure(BigDecimal.valueOf(5_000_000));
        dto.setTotalVaR99(BigDecimal.valueOf(90000));
        dto.setTotalVaR95(BigDecimal.valueOf(55000));

        RegulatoryReportDto.SymbolRiskSection section =
                new RegulatoryReportDto.SymbolRiskSection();
        section.setSymbol("AAPL");
        section.setExposure(BigDecimal.valueOf(1_200_000));
        section.setVar99(BigDecimal.valueOf(25000));
        section.setVar95(BigDecimal.valueOf(15000));

        dto.setSymbols(Collections.singletonList(section));

        return dto;
    }

    public List<RegulatoryLimitBreachDto> checkBreaches() {

        RegulatoryLimitBreachDto breach = new RegulatoryLimitBreachDto();
        breach.setSymbol("TSLA");
        breach.setTimestamp(System.currentTimeMillis());
        breach.setRuleName("Basel III Exposure Limit");
        breach.setThreshold(BigDecimal.valueOf(1000000));
        breach.setActualValue(BigDecimal.valueOf(1500000));
        breach.setBreached(true);

        return Collections.singletonList(breach);
    }
}
