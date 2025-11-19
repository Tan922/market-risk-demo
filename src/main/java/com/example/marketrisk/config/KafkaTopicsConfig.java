package com.example.marketrisk.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

@Configuration
public class KafkaTopicsConfig {

    @Value("${app.kafka.topics.market-data-topic}")
    private String marketDataTopic;

    @Value("${app.kafka.topics.market-risk-snapshot-topic}")
    private String marketRiskSnapshotTopic;

    @Value("${app.kafka.topics.market-risk-alert-topic}")
    private String marketRiskAlertTopic;

    @Value("${app.kafka.topics.regulatory-reporting-topic}")
    private String regulatoryReportingTopic;

    // High-throughput topics: increase partitions
    @Bean
    public NewTopic marketdataRealtimeTopic() {
        return new NewTopic(marketDataTopic, 6, (short) 1); // 6 partitions
    }

    @Bean
    public NewTopic marketdataEnrichedTopic() {
        return new NewTopic(marketRiskSnapshotTopic, 6, (short) 1); // 6 partitions
    }

    // Normal throughput topics
    @Bean
    public NewTopic riskCalculationsTopic() {
        return new NewTopic(marketRiskAlertTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic riskCalculationsSaveTopic() {
        return new NewTopic(regulatoryReportingTopic, 1, (short) 1);
    }

}
