package com.example.marketrisk.kafka.stream;

import com.example.marketrisk.kafka.JsonSerdes;
import com.example.marketrisk.kafka.processor.EnrichProcessor;
import com.example.marketrisk.model.EnrichedMarketData;
import com.example.marketrisk.model.MarketData;
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
public class MarketDataEnrichStream {

    @Value("${app.kafka.topics.market-data-realtime}")
    private String marketDataRealtimeTopic;

    @Value("${app.kafka.topics.market-data-enriched}")
    private String marketDataEnrichedTopic;

    @Value("${app.kafka.topics.market-data-enriched-save}")
    private String marketDataEnrichedSaveTopic;

    // Read from market-data-realtime topic, enrich data, then forward
    @Bean
    public KStream<String, EnrichedMarketData> enrichmentPipeline(StreamsBuilder builder, EnrichProcessor processor) {
        KStream<String, MarketData> inputStream = builder.stream(marketDataRealtimeTopic);

        KStream<String, EnrichedMarketData> enriched = inputStream.mapValues(processor::enrich);

        enriched.to(marketDataEnrichedTopic, Produced.with(Serdes.String(), JsonSerdes.enrichedMarketData()));
        enriched.to(marketDataEnrichedSaveTopic, Produced.with(Serdes.String(), JsonSerdes.enrichedMarketData()));

        return enriched;
    }
}
