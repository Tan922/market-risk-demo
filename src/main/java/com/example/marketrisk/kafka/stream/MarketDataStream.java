package com.example.marketrisk.kafka.stream;

import com.example.marketrisk.kafka.JsonSerdes;
import com.example.marketrisk.kafka.processor.EnrichProcessor;
import com.example.marketrisk.model.EnrichedMarketData;
import com.example.marketrisk.model.MarketData;
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
public class MarketDataStream {

    @Value("${app.kafka.topics.market-data-realtime}")
    private String marketDataRealtimeTopic;

    @Value("${app.kafka.topics.market-data-enriched}")
    private String marketDataEnrichedTopic;

    @Value("${app.kafka.topics.market-data-enriched-save}")
    private String marketDataEnrichedSaveTopic;

    @Bean
    public KStream<String, MarketData> enrichmentPipeline(
            StreamsBuilder builder,
            EnrichProcessor processor
    ) {

        KStream<String, MarketData> input = builder.stream(marketDataRealtimeTopic);

        input.peek((k, v) -> log.info("STREAM READ MarketData = {}", v));

        KStream<String, EnrichedMarketData> enriched =
                input.mapValues(processor::enrich);

        enriched.peek((k, v) -> log.info("STREAM ENRICHED OUTPUT = {}", v));

        enriched.to(marketDataEnrichedTopic);
        enriched.to(marketDataEnrichedSaveTopic);

        return input;
    }
}

