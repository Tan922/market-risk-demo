package com.example.marketrisk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Value("${ai.api.base-url}")
    private String url;

    @Value("${ai.api.key}")
    private String key;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Authorization", "Bearer " + key)
                .build();
    }
}