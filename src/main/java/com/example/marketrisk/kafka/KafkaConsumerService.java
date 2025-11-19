package com.example.marketrisk.kafka;

import com.example.marketrisk.model.RegulatoryReport;
import com.example.marketrisk.repository.RegulatoryReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final RegulatoryReportRepository regulatoryReportRepository;

    @KafkaListener(
            topics = "${app.kafka.topics.regulatory-reporting-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void generateAndSaveReport(RegulatoryReport data) {
        try {
            regulatoryReportRepository.save(data.toEntity());
        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
        }
    }

    @KafkaListener(
            topics = "${app.kafka.topics.market-risk-alert-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {
                    "value.deserializer=org.apache.kafka.common.serialization.StringDeserializer"
            }
    )
    public void onAlert(String data) {
        System.out.println("#################### Demo risk alert: " + data + " ####################");
    }
}
