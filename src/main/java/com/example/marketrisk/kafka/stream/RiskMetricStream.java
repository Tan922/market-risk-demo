package com.example.marketrisk.kafka.stream;

import com.example.marketrisk.kafka.JsonSerdes;
import com.example.marketrisk.kafka.processor.RiskMetricCalculationProcessor;
import com.example.marketrisk.model.EnrichedMarketData;
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
public class RiskMetricStream {

    @Value("${app.kafka.topics.market-data-enriched}")
    private String marketDataEnrichedTopic;

    @Value("${app.kafka.topics.risk-metrics}")
    private String riskMetricsTopic;

    @Value("${app.kafka.topics.risk-metrics-save}")
    private String riskMetricsSaveTopic;

    // Read from market-data-enriched topic, calculate risk metrics, then forward
    @Bean
    public KStream<String, RiskMetric> riskMetricPipeline(
            StreamsBuilder builder,
            RiskMetricCalculationProcessor processor
    ) {
        KStream<String, EnrichedMarketData> input = builder.stream(marketDataEnrichedTopic);

        input.peek((k, v) -> log.info("STREAM READ Enriched = {}", v));

        KStream<String, RiskMetric> out =
                input.mapValues(processor::computeRisk);

        out.peek((k, v) -> log.info("RISK METRIC OUTPUT = {}", v));

        out.to(riskMetricsTopic);
        out.to(riskMetricsSaveTopic);

        return out;
    }
}
