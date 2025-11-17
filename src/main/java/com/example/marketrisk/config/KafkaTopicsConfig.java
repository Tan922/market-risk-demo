package com.example.marketrisk.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {

    @Value("${app.kafka.topics.market-data-realtime}")
    private String marketDataRealtimeTopic;

    @Value("${app.kafka.topics.market-data-realtime-save}")
    private String marketDataRealtimeSaveTopic;

    @Value("${app.kafka.topics.market-data-enriched}")
    private String marketDataEnrichedTopic;

    @Value("${app.kafka.topics.market-data-enriched-save}")
    private String marketDataEnrichedSaveTopic;

    @Value("${app.kafka.topics.risk-metrics}")
    private String riskMetricsTopic;

    @Value("${app.kafka.topics.risk-metrics-save}")
    private String riskMetricsetricsSaveTopic;

    @Value("${app.kafka.topics.risk-alerts}")
    private String riskAlertsTopic;

    // High-throughput topics: increase partitions
    @Bean
    public NewTopic marketdataRealtimeTopic() {
        return new NewTopic(marketDataRealtimeTopic, 6, (short) 1); // 6 partitions
    }

    @Bean
    public NewTopic marketdataRealtimeSaveTopic() {
        return new NewTopic(marketDataRealtimeSaveTopic, 6, (short) 1); // 6 partitions
    }

    @Bean
    public NewTopic marketdataEnrichedTopic() {
        return new NewTopic(marketDataEnrichedTopic, 6, (short) 1); // 6 partitions
    }
    @Bean
    public NewTopic marketdataEnrichedSaveTopic() {
        return new NewTopic(marketDataEnrichedSaveTopic, 6, (short) 1); // 6 partitions
    }

    // Normal throughput topics
    @Bean
    public NewTopic riskCalculationsTopic() {
        return new NewTopic(riskMetricsTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic riskCalculationsSaveTopic() {
        return new NewTopic(riskMetricsetricsSaveTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic riskAlertsTopic() {
        return new NewTopic(riskAlertsTopic, 1, (short) 1);
    }

}
