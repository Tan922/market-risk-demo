package com.example.marketrisk.kafka;

import com.example.marketrisk.model.MarketData;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> template;

    @Value("${app.kafka.topics.marketdata-realtime}")
    private String marketDatarealtimeTopic;

    public void publish(MarketData data) {
        template.send(marketDatarealtimeTopic, data.getSymbol(), data);
    }

    public void publish(String topic, Object value) {
        template.send(topic, value);
    }
}
