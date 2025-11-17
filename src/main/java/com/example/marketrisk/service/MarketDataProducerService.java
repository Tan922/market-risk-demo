package com.example.marketrisk.service;

import com.example.marketrisk.model.MarketData;
import com.example.marketrisk.model.dto.MarketDataRequest;
import com.example.marketrisk.model.dto.MarketDataResponse;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MarketDataProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.market-data-realtime}")
    private String marketDataRealtimeTopic;

    private final Random random = new Random();

    private ScheduledExecutorService executor;
    @Getter
    private int ratePerSecond;
    @Getter
    private boolean running = false;

    // Symbol list for simulation
    private final String[] symbols = {"SYM1", "SYM2", "SYM3", "SYM4", "SYM5"};

    // Track last prices for volatility simulation
    private final Map<String, BigDecimal> lastPrices = new HashMap<>(){{
        for (String s : symbols) {
            put(s, BigDecimal.ZERO);
        }
    }};

    public MarketDataProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Start generator at given rate (messages/sec)
     */
    public synchronized void start(int ratePerSecond) {
        if (running) return;

        this.ratePerSecond = ratePerSecond;
        int periodMs = 1000 / ratePerSecond; // interval between messages in ms

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::sendRandomTick, 0, periodMs, TimeUnit.MILLISECONDS);
        running = true;

        log.info("MarketDataProducerService started at {} msg/sec", ratePerSecond);
    }

    /**
     * Stop generator
     */
    public synchronized void stop() {
        if (!running) return;
        executor.shutdownNow();
        running = false;
        log.info("MarketDataProducerService stopped");
    }

    @PreDestroy
    public void shutdown() {
        stop();
    }

    /**
     * Generate a random MarketData tick and push to Kafka asynchronously
     */
    private void sendRandomTick() {
        try {
            MarketData tick = generateRandomTick();
            kafkaTemplate.send(marketDataRealtimeTopic, tick);
            // optional debug log, comment out for high throughput
            // log.debug("Sent tick: {}", tick);
        } catch (Exception e) {
            log.error("Error sending tick", e);
        }
    }

    /**
     * Create a random MarketData object
     */
    private MarketData generateRandomTick() {
        MarketData data = new MarketData();

        // Pick a random symbol
        String symbol = symbols[random.nextInt(symbols.length)];
        data.setSymbol(symbol);

        // Previous price for volatility
        BigDecimal lastPrice = lastPrices.get(symbol);

        // Simulate normal price movement
        double change = random.nextGaussian() * 0.2; // small normal movement

        // Simulate occasional bursts / spikes
        if (random.nextDouble() < 0.05) { // 5% chance of spike
            change += random.nextGaussian() * 5; // bigger spike
        }

        BigDecimal newPrice = lastPrice.add(BigDecimal.valueOf(change)).max(BigDecimal.valueOf(1));
        lastPrices.put(symbol, newPrice);
        data.setPrice(newPrice);

        // Volume also varies with bursts
        int baseVolume = 50 + random.nextInt(200);
        if (random.nextDouble() < 0.05) {
            baseVolume *= 10; // burst volume
        }
        data.setVolume(BigDecimal.valueOf(baseVolume));

        data.setTimestamp(Instant.now().toEpochMilli());

        return data;
    }

    /**
     * Inject a single manual tick
     */
    public MarketDataResponse injectManual(MarketDataRequest request) {
        MarketData tick = MarketData.fromRequest(request);

        kafkaTemplate.send(marketDataRealtimeTopic, tick.getSymbol(), tick);
        return tick.toResponse();
    }
}
