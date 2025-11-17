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

    @Value("${app.kafka.topics.market-data-realtime}")
    private String marketDataRealtimeTopic;

    @Value("${app.kafka.topics.market-data-realtime-save}")
    private String marketDataRealtimeSaveTopic;

    public void publish(MarketData data) {
        template.send(marketDataRealtimeTopic, data.getSymbol(), data);
        template.send(marketDataRealtimeSaveTopic, data.getSymbol(), data);
    }

}
