package com.example.marketrisk.kafka;

import com.example.marketrisk.model.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class JsonSerdes {

    private static <T> Serde<T> buildSerde(Class<T> clazz) {
        JsonSerializer<T> serializer = new JsonSerializer<>();

        JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<MarketData> marketData() {
        return buildSerde(MarketData.class);
    }

    public static Serde<EnrichedMarketData> enrichedMarketData() {
        return buildSerde(EnrichedMarketData.class);
    }

    public static Serde<RiskMetric> riskMetrics() {
        return buildSerde(RiskMetric.class);
    }

    public static Serde<AlertEvent> riskAlert() {
        return buildSerde(AlertEvent.class);
    }
}
