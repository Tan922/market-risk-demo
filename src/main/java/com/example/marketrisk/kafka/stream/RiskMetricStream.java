package com.example.marketrisk.kafka.stream;

import com.example.marketrisk.kafka.JsonSerdes;
import com.example.marketrisk.kafka.processor.RiskMetricCalculationProcessor;
import com.example.marketrisk.model.EnrichedMarketData;
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
public class RiskMetricStream {

    @Value("${app.kafka.topics.market-data-enriched}")
    private String marketDataEnrichedTopic;

    @Value("${app.kafka.topics.risk-metrics}")
    private String riskMetricsTopic;

    @Value("${app.kafka.topics.risk-metrics-save}")
    private String riskMetricsetricsSaveTopic;

    // Read from market-data-enriched topic, calculate risk metrics, then forward
    @Bean
    public KStream<String, RiskMetric> enrichmentPipeline(StreamsBuilder builder, RiskMetricCalculationProcessor processor) {

        KStream<String, EnrichedMarketData> inputStream = builder.stream(marketDataEnrichedTopic);

        KStream<String, RiskMetric> metrics = inputStream.mapValues(processor::computeRisk);

        metrics.to(riskMetricsTopic, Produced.with(Serdes.String(), JsonSerdes.riskMetrics()));
        metrics.to(riskMetricsetricsSaveTopic, Produced.with(Serdes.String(), JsonSerdes.riskMetrics()));

        return metrics;

    }
}
