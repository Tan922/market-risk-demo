package com.example.marketrisk.service;

import com.example.marketrisk.model.dto.RiskMetricsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AiService {

    private final RiskService riskService;

    private final WebClient webClient = WebClient.builder().build();

    @Value("${ai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    @Value("${ai.api.key:demo-key}")
    private String apiKey;

    public String generateInsight(String request) {
        RiskMetricsDto data = riskService.calculateRisk(request);

        String prompt = "Provide a risk summary based on the following data:\n" + data;

        return webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue("""
                {
                  "model": "gpt-4o-mini",
                  "messages": [
                    {"role":"user","content": "%s"}
                  ]
                }
                """.formatted(prompt))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
