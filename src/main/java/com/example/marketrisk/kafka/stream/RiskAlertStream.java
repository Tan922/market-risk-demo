package com.example.marketrisk.kafka.stream;

import com.example.marketrisk.kafka.JsonSerdes;
import com.example.marketrisk.kafka.processor.RiskAlertGenerationProcessor;
import com.example.marketrisk.model.AlertEvent;
import com.example.marketrisk.model.RiskMetric;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RiskAlertStream {
    @Value("${app.kafka.topics.risk-metrics}")
    private String riskMetricsTopic;

    @Value("${app.kafka.topics.risk-alerts}")
    private String riskAlertsTopic;

    // Read from risk-metrics topic, generate alerts based on risk metrics, then forward
    @Bean
    public KStream<String, AlertEvent> alertPipeline(StreamsBuilder builder, RiskAlertGenerationProcessor processor) {

        KStream<String, RiskMetric> inputStream = builder.stream(riskMetricsTopic);

        KStream<String, AlertEvent> alerts = inputStream.filter((k, v) -> {
            // simple demo: trigger alert if riskScore > 900
            try {
                return v.getVar99().floatValue() > 900;
            } catch (Exception e) {
                return false;
            }
        }).mapValues(processor::generateAlerts);
        alerts.to(riskAlertsTopic, Produced.with(Serdes.String(), JsonSerdes.riskAlert()));

        return alerts;
    }
}
