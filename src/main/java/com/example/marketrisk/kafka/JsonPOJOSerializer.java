package com.example.marketrisk.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;

public class JsonPOJOSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper;

    public JsonPOJOSerializer(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) { }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            if (data == null) return null;
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("JSON serialization error for topic " + topic, e);
        }
    }

    @Override
    public void close() { }
}
