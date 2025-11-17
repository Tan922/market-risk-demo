package com.example.marketrisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class MarketRiskDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketRiskDemoApplication.class, args);
    }
}
