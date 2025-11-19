package com.example.marketrisk.service;

import com.example.marketrisk.model.MarketRiskSnapshot;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MarketRiskSnapshotService {

    @Value("${app.kafka.stores.market-risk-snapshot-store:market-risk-snapshot-store}")
    private String snapshotStoreName;

    private final StreamsBuilderFactoryBean streamsFactory;

    public MarketRiskSnapshotService(StreamsBuilderFactoryBean streamsFactory) {
        this.streamsFactory = streamsFactory;
    }

    private ReadOnlyKeyValueStore<String, MarketRiskSnapshot> getStoreSafely() {
        KafkaStreams streams = streamsFactory.getKafkaStreams();
        if (streams == null) {
            log.warn("KafkaStreams instance is null (not started yet)");
            return null;
        }
        try {
            if (!streams.state().isRunningOrRebalancing()) {
                log.warn("KafkaStreams not running (state={})", streams.state());
                return null;
            }
            return streams.store(
                    StoreQueryParameters.fromNameAndType(
                            snapshotStoreName,
                            QueryableStoreTypes.keyValueStore()
                    )
            );
        } catch (InvalidStateStoreException e) {
            log.warn("Store '{}' not ready yet", snapshotStoreName);
            return null;
        }
    }

    public MarketRiskSnapshot getLatestSnapshot(String symbol) {
        ReadOnlyKeyValueStore<String, MarketRiskSnapshot> store = getStoreSafely();
        return store == null ? null : store.get(symbol);
    }

    public List<MarketRiskSnapshot> getAllSnapshots() {
        ReadOnlyKeyValueStore<String, MarketRiskSnapshot> store = getStoreSafely();
        if (store == null) return List.of();
        List<MarketRiskSnapshot> out = new ArrayList<>();
        try (var iter = store.all()) {
            while (iter.hasNext()) out.add(iter.next().value);
        }
        return out;
    }
}
