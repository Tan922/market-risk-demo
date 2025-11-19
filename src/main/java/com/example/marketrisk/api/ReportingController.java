package com.example.marketrisk.api;

import com.example.marketrisk.model.RegulatoryReport;
import com.example.marketrisk.model.entity.RegulatoryReportEntity;
import com.example.marketrisk.service.ReportingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reporting")
@Tag(name = "Regulatory Reporting", description = "Regulatory and compliance reporting endpoints")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/report/{type}")
    @Operation(
            summary = "Generate a regulatory report",
            description = "Returns a detailed regulatory report (e.g. Basel III, FRTB)."
    )
    @ApiResponse(responseCode = "200", description = "Report successfully generated")
    public List<RegulatoryReportEntity> generateReport(@PathVariable String type) {
        return reportingService.getReportsByType(type);
    }

}
