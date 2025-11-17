package com.example.marketrisk.kafka.stream;

import com.example.marketrisk.kafka.JsonSerdes;
import com.example.marketrisk.kafka.processor.RiskAlertGenerationProcessor;
import com.example.marketrisk.model.AlertEvent;
import com.example.marketrisk.model.RiskMetric;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RiskAlertStream {
    @Value("${app.kafka.topics.risk-metrics}")
    private String riskMetricsTopic;

    @Value("${app.kafka.topics.risk-alerts}")
    private String riskAlertsTopic;

    // Read from risk-metrics topic, generate alerts based on risk metrics, then forward
    @Bean
    public KStream<String, AlertEvent> alertPipeline(
            StreamsBuilder builder,
            RiskAlertGenerationProcessor processor
    ) {
        KStream<String, RiskMetric> input = builder.stream(riskMetricsTopic);

        input.peek((k, v) -> log.info("STREAM READ RiskMetric = {}", v));

        KStream<String, AlertEvent> out = input
                .filter((k, v) -> v.getVar99().floatValue() > 900)
                .mapValues(processor::generateAlerts);

        out.peek((k, v) -> log.info("ALERT OUTPUT = {}", v));

        out.to(riskAlertsTopic);

        return out;
    }

}
