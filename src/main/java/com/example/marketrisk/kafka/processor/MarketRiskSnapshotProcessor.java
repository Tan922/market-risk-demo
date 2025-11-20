package com.example.marketrisk.kafka.processor;

import com.example.marketrisk.model.MarketData;
import com.example.marketrisk.model.MarketRiskSnapshot;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class MarketRiskSnapshotProcessor implements Transformer<String, MarketData, KeyValue<String, MarketRiskSnapshot>> {

    private final String marketDataStoreName;
    private KeyValueStore<String, MarketData> store;
    private ProcessorContext context;

    public MarketRiskSnapshotProcessor(String marketDataStoreName) {
        this.marketDataStoreName = marketDataStoreName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        this.context = context;
        this.store = (KeyValueStore<String, MarketData>) context.getStateStore(marketDataStoreName);
        log.warn("🔥 MarketRiskSnapshotProcessor INIT — store={} ", marketDataStoreName);
    }

    @Override
    public KeyValue<String, MarketRiskSnapshot> transform(String key, MarketData value) {
        log.info("Received MarketData: key={}, value={}", key, value);
        try {
            if (key == null) key = value.getSymbol();

            MarketData prev = store.get(key);
            // compute snapshot
            MarketRiskSnapshot s = new MarketRiskSnapshot();
            s.setSymbol(value.getSymbol());
            s.setTimestamp(value.getTimestamp());
            s.setPrice(value.getPrice());
            s.setVolume(value.getVolume());

            // safe calculations using prev if available
            if (prev != null && prev.getPrice() != null && value.getPrice() != null) {
                // simple daily pnl = (last - prev) * volume
                BigDecimal dailyPnl = value.getPrice().subtract(prev.getPrice()).multiply(value.getVolume());
                s.setDailyPnl(dailyPnl);
            } else {
                s.setDailyPnl(BigDecimal.ZERO);
            }

            // some placeholder metrics
            s.setMonthlyPnl(BigDecimal.ZERO);
            s.setVolatility30(BigDecimal.ZERO);
            s.setVar1at95(BigDecimal.ZERO);
            s.setVar1at99(BigDecimal.ZERO);
            s.setVar10at95(BigDecimal.ZERO);
            s.setVar10at99(BigDecimal.ZERO);
            s.setAlpha(BigDecimal.ZERO);
            s.setBeta(BigDecimal.ONE);
            s.setRiskScore(s.getDailyPnl() != null ? s.getDailyPnl().intValue() : 0);
            s.setAiSentiment("neutral");
            s.setAiSummary("");
            s.setAiConfidenceScore(BigDecimal.ZERO);

            // update store with latest tick
            store.put(key, value);

            // forward snapshot
            log.info("Generated Snapshot: {}", s);
            return KeyValue.pair(key, s);
        } catch (Exception e) {
            log.error("Error in MarketRiskSnapshotProcessor transform", e);
            return null;
        }
    }

    @Override
    public void close() {}
}
