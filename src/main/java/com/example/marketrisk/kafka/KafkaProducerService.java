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

    @Value("${app.kafka.topics.market-data-topic}")
    private String marketDataTopic;

    public void publish(MarketData data) {
        template.send(marketDataTopic, data.getSymbol(), data);
    }

}
