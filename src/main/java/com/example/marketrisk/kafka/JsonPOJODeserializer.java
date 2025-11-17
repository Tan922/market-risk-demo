package com.example.marketrisk.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JsonPOJODeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> targetType;

    public JsonPOJODeserializer(ObjectMapper mapper, Class<T> targetType) {
        this.objectMapper = mapper;
        this.targetType = targetType;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) { }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) return null;
            return objectMapper.readValue(bytes, targetType);
        } catch (Exception e) {
            throw new RuntimeException("JSON deserialization error for topic " + topic, e);
        }
    }

    @Override
    public void close() { }
}
