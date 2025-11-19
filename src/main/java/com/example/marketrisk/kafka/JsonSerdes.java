package com.example.marketrisk.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class JsonSerdes {

    private static final ObjectMapper MAPPER = objectMapper();

    private JsonSerdes() {}

    private static ObjectMapper objectMapper() {
        ObjectMapper m = new ObjectMapper();
        m.findAndRegisterModules(); // register JavaTimeModule, JDK8 modules etc.
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return m;
    }

    public static <T> Serde<T> serdeFor(Class<T> clazz) {
        Serializer<T> serializer = (topic, data) -> {
            try {
                return MAPPER.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        Deserializer<T> deserializer = (topic, bytes) -> {
            if (bytes == null || bytes.length == 0) return null;
            try {
                return MAPPER.readValue(bytes, clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<com.example.marketrisk.model.MarketData> marketData() {
        return serdeFor(com.example.marketrisk.model.MarketData.class);
    }

    public static Serde<com.example.marketrisk.model.MarketRiskSnapshot> marketRiskSnapshot() {
        return serdeFor(com.example.marketrisk.model.MarketRiskSnapshot.class);
    }

    public static Serde<com.example.marketrisk.model.AlertEvent> alertEvent() {
        return serdeFor(com.example.marketrisk.model.AlertEvent.class);
    }
}
