package com.example.marketrisk.api;

import com.example.marketrisk.model.dto.MarketDataRequest;
import com.example.marketrisk.model.dto.MarketDataResponse;
import com.example.marketrisk.service.MarketDataProducerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marketdata")
@RequiredArgsConstructor
public class MarketDataController {

    private final MarketDataProducerService producerService;

    @Operation(summary = "Start raw market data generator")
    @PostMapping("/start")
    public String start(@RequestParam(defaultValue = "100") int ratePerSecond) {
        producerService.start(ratePerSecond);
        return "Generator started at " + ratePerSecond + " msg/sec";
    }

    @Operation(summary = "Stop raw market data generator")
    @PostMapping("/stop")
    public String stop() {
        producerService.stop();
        return "Raw data generator stopped";
    }

    @Operation(summary = "Check generator status")
    @GetMapping("/status")
    public String status() {
        return producerService.isRunning()
                ? "RUNNING at " + producerService.getRatePerSecond() + " msg/sec"
                : "STOPPED";
    }

    @Operation(summary = "Inject a manual market tick (debug/demo)")
    @PostMapping("/inject")
    public MarketDataResponse inject(@RequestBody MarketDataRequest request) {
        return producerService.injectManual(request);
    }
}
