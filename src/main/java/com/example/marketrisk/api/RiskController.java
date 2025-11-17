package com.example.marketrisk.api;

import com.example.marketrisk.model.dto.RiskMetricsDto;
import com.example.marketrisk.service.RiskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

//    @Operation(summary = "Get latest positions")
//    @GetMapping("/positions")
//    public List<String> getPositions() {
//        return riskService.getLatestPositions();
//    }
//
//    @Operation(summary = "Get latest risk alerts")
//    @GetMapping("/alerts")
//    public List<String> getAlerts() {
//        return riskService.getLatestAlerts();
//    }

    @Operation(summary = "Recalculate all risk metrics for a given symbol")
    @GetMapping("/{symbol}")
    public RiskMetricsDto calculate(@RequestParam String symbol) {
        return riskService.calculateRisk(symbol);
    }
}
