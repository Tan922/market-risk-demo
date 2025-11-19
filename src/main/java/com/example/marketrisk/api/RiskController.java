package com.example.marketrisk.api;

import com.example.marketrisk.model.MarketRiskSnapshot;
import com.example.marketrisk.service.MarketRiskSnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/risk")
@Tag(name = "Market Risk Snapshots", description = "Endpoints for retrieving market risk snapshots")
public class RiskController {

    private final MarketRiskSnapshotService snapshotService;

    @GetMapping("/{symbol}")
    @Operation(
            summary = "Get latest market risk snapshot by symbol",
            description = "Retrieves the most recent market risk snapshot for the specified symbol."
    )
    public MarketRiskSnapshot getBySymbol(@PathVariable String symbol) {
        return snapshotService.getLatestSnapshot(symbol);
    }

    @GetMapping
    @Operation(
            summary = "Get all market risk snapshots",
            description = "Retrieves all available market risk snapshots."
    )
    public List<MarketRiskSnapshot> getAll() {
        return snapshotService.getAllSnapshots();
    }
}
