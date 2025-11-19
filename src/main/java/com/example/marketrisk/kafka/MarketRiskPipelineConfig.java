package com.example.marketrisk.kafka;

import com.example.marketrisk.kafka.processor.MarketRiskSnapshotProcessor;
import com.example.marketrisk.kafka.processor.RiskAlertProcessor;
import com.example.marketrisk.model.AlertEvent;
import com.example.marketrisk.model.MarketData;
import com.example.marketrisk.model.MarketRiskSnapshot;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MarketRiskPipelineConfig {

    @Value("${app.kafka.topics.market-data-topic:market-data}")
    private String marketDataTopic;

    @Value("${app.kafka.topics.market-risk-snapshot-topic:market-risk-snapshot-out}")
    private String marketRiskSnapshotTopic;

    @Value("${app.kafka.topics.market-risk-alert-topic:market-risk-alerts}")
    private String marketRiskAlertTopic;

    @Value("${app.kafka.stores.market-data-store:market-data-store}")
    private String marketDataStore; // used by processor to keep last tick per symbol

    @Value("${app.kafka.stores.market-risk-snapshot-store:market-risk-snapshot-store}")
    private String marketRiskSnapshotStore; // queryable store name

    @Bean
    public KStream<String, MarketData> marketRiskPipeline(StreamsBuilder builder) {

        // 1) create processor-friendly state store for market-data history (we only keep last tick here)
        builder.addStateStore(
                Stores.keyValueStoreBuilder(
                        Stores.persistentKeyValueStore(marketDataStore),
                        Serdes.String(),
                        JsonSerdes.marketData()
                )
        );

        // 2) source stream: market data input
        KStream<String, MarketData> input = builder.stream(
                marketDataTopic,
                Consumed.with(Serdes.String(), JsonSerdes.marketData())
        );

        // 3) transform incoming market data into MarketRiskSnapshot using Processor API (Transformer)
        KStream<String, MarketRiskSnapshot> snapshots = input.transform(
                () -> new MarketRiskSnapshotProcessor(marketDataStore),
                Named.as("market-risk-snapshot-transform"),
                marketDataStore
        );

        // 4) publish snapshots to an output topic (this is the canonical snapshot topic)
        snapshots.to(marketRiskSnapshotTopic, Produced.with(Serdes.String(), JsonSerdes.marketRiskSnapshot()));

        // 5) create a queryable KTable from that snapshot output topic (materialized store)
        builder.table(
                marketRiskSnapshotTopic,
                Consumed.with(Serdes.String(), JsonSerdes.marketRiskSnapshot()),
                Materialized.<String, MarketRiskSnapshot>as(
                                Stores.persistentKeyValueStore(marketRiskSnapshotStore)
                        ).withKeySerde(Serdes.String())
                        .withValueSerde(JsonSerdes.marketRiskSnapshot())
        );

        // 6) run alerting processor downstream from snapshots (no state store required)
        KStream<String, AlertEvent> alerts = snapshots.process(
                RiskAlertProcessor::new,
                Named.as("risk-alert-processor")
        );

        alerts.to(marketRiskAlertTopic, Produced.with(Serdes.String(), JsonSerdes.alertEvent()));

        return input;
    }
}
