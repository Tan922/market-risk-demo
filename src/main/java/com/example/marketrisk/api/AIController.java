package com.example.marketrisk.api;

import com.example.marketrisk.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AI Controller", description = "Endpoints for AI-driven risk insights")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AiService aiService;

    @Operation(summary = "Generate AI-driven real-time risk insights (agentic)")
    @GetMapping("/insights/realtime")
    public String realtimeInsight(@RequestParam(defaultValue = "general-summary") String type) {
        return aiService.generateInsight(type);
    }

    @Operation(summary = "Generate AI-driven risk insights (agentic)")
    @PostMapping("/insights")
    public String insights(
            @RequestBody String request,
            @RequestParam(defaultValue = "general-summary") String type
    )
    {
        return aiService.generateInsight(request, type);
    }
}
