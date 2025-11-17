package com.example.marketrisk.kafka;

import com.example.marketrisk.model.MarketData;
import com.example.marketrisk.model.RiskMetric;
import com.example.marketrisk.repository.EnrichedMarketDataRepository;
import com.example.marketrisk.repository.MarketDataRepository;
import com.example.marketrisk.repository.RiskMetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.marketrisk.model.EnrichedMarketData;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final MarketDataRepository marketDataRepository;
    private final EnrichedMarketDataRepository enrichedMarketDataRepository;
    private final RiskMetricRepository riskMetricRepository;

    @KafkaListener(
            topics = "${app.kafka.topics.market-data-realtime-save}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void saveMarketData(MarketData data) {
        try {
            marketDataRepository.save(data.toEntity());
        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
        }
    }

    @KafkaListener(
            topics = "${app.kafka.topics.market-data-enriched-save}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void saveEnrichedMarketData(EnrichedMarketData data) {
        try {
            enrichedMarketDataRepository.save(data.toEntity());
        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
        }
    }

    @KafkaListener(
            topics = "${app.kafka.topics.risk-metrics-save}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void saveRiskMetrics(RiskMetric data) {
        try {
            riskMetricRepository.save(data.toEntity());
        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
        }
    }

    @KafkaListener(
            topics = "${app.kafka.topics.risk-alerts}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {
                    "value.deserializer=org.apache.kafka.common.serialization.StringDeserializer"
            }
    )
    public void onAlert(String data) {
        System.out.println("#################### Demo risk alert: " + data + " ####################");
    }
}
