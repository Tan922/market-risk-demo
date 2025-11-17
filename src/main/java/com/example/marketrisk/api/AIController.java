package com.example.marketrisk.api;

import com.example.marketrisk.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AiService aiService;

    @Operation(summary = "Generate AI-driven risk insights (agentic)")
    @PostMapping("/insights")
    public String insights(@RequestBody String request) {
        return aiService.generateInsight(request);
    }
}
