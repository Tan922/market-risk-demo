package com.example.marketrisk.service;

import com.example.marketrisk.model.dto.RiskMetricsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final RiskService riskService;
    private final WebClient webClient;

    @Value("${ai.api.model}")
    private String model;

    public AiService(RiskService riskService,
                     @Value("${ai.api.base-url}") String aiUrl,
                     @Value("${ai.api.key}") String apiKey) {

        this.riskService = riskService;

        this.webClient = WebClient.builder()
                .baseUrl(aiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();
    }

    public String generateInsight(String request) {
        RiskMetricsDto data = riskService.calculateRisk(request);

        String prompt = "Provide a risk summary based on:\n" + data;

        Map<String, Object> payload = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        return webClient.post()
                .uri("/chat/completions")   // appended to base URL
                .bodyValue(payload)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new RuntimeException("AI API error: " + response.statusCode() + " - " + body)
                                ))
                )
                .bodyToMono(String.class)
                .block();
    }


}

