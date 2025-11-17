package com.example.marketrisk.service;

import com.example.marketrisk.model.entity.MarketDataEntity;
import com.example.marketrisk.repository.MarketDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketDataService {

    private final MarketDataRepository repository;

    public MarketDataEntity save(MarketDataEntity data) {
        return repository.save(data);
    }

    public List<MarketDataEntity> findAll() {
        return repository.findAll();
    }

    public List<MarketDataEntity> findLatestBySymbol(String symbol) {
        return repository.findTopBySymbolOrderByTimestampDesc(symbol);
    }
}
