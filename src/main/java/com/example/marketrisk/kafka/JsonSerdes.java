package com.example.marketrisk.kafka;

import com.example.marketrisk.model.AlertEvent;
import com.example.marketrisk.model.EnrichedMarketData;
import com.example.marketrisk.model.MarketData;
import com.example.marketrisk.model.RiskMetric;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

public class JsonSerdes {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())        // support Java time types
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // --------------------------------------------------------------------------------------------
    // Generic factory methods
    // --------------------------------------------------------------------------------------------
    public static <T> Serde<T> serdeFor(Class<T> clazz) {
        Serializer<T> serializer = new JsonPOJOSerializer<>(mapper);
        Deserializer<T> deserializer = new JsonPOJODeserializer<>(mapper, clazz);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    // --------------------------------------------------------------------------------------------
    // Type-specific convenience Serdes
    // --------------------------------------------------------------------------------------------
    public static Serde<MarketData> marketData() {
        return serdeFor(MarketData.class);
    }

    public static Serde<EnrichedMarketData> enrichedMarketData() {
        return serdeFor(EnrichedMarketData.class);
    }

    public static Serde<RiskMetric> riskMetrics() {
        return serdeFor(RiskMetric.class);
    }

    public static Serde<AlertEvent> riskAlert() {
        return serdeFor(AlertEvent.class);
    }

}
