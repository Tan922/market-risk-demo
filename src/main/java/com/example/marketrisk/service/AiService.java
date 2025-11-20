package com.example.marketrisk.service;

import com.example.marketrisk.model.MarketRiskSnapshot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final MarketRiskSnapshotService service;
    private final WebClient webClient;
    private final ObjectMapper mapper;
    private final Environment env;  // to read prompt templates from application.yml

    @Value("${ai.api.model}")
    private String model;

    public String generateInsight(String promptType) {
        List<MarketRiskSnapshot> data = service.getAllSnapshots();

        // load prompt template from application.yml
        String template = env.getProperty("ai.prompts." + promptType);
        if (template == null) {
            template = env.getProperty("ai.prompts.general-summary"); // fallback
        }

        // replace placeholder with actual data
        String prompt = template.replace("{risk_data}", data.toString());

        Map<String, Object> payload = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        return askAI(payload);
    }

    public String generateInsight(String symbol, String promptType) {
        MarketRiskSnapshot data = service.getLatestSnapshot(symbol);

        // load prompt template from application.yml
        String template = env.getProperty("ai.prompts." + promptType);
        if (template == null) {
            template = env.getProperty("ai.prompts.general-summary"); // fallback
        }

        // replace placeholder with actual data
        String prompt = template.replace("{risk_data}", data == null? "null" :data.toString());

        Map<String, Object> payload = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        return askAI(payload);
    }

    private String askAI(Map<String, Object> payload) {
        String block = webClient.post()
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
        return parseAIResponse(block);
    }

    private String parseAIResponse(String response) {
        JsonNode root = null;
        try {
            root = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            return "AI API error: " + e.getMessage();
        }

        // Navigate to choices[0].message.content
        return root.path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();
    }

}

