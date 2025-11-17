package com.example.marketrisk.service;

import com.example.marketrisk.model.entity.EnrichedMarketDataEntity;
import com.example.marketrisk.repository.EnrichedMarketDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableauExportService {

    private final EnrichedMarketDataRepository repository;

    public String exportCsv() {
        List<EnrichedMarketDataEntity> data = repository.findAll();

        StringWriter sw = new StringWriter();
        sw.write("symbol,price,volume,notional,riskScore,timestamp\n");

        data.forEach(e -> sw.write(
                String.format("%s,%s,%s,%s,%s,%d\n",
                        e.getSymbol(),
                        e.getPrice(),
                        e.getVolume(),
                        e.getNotional(),
                        e.getRiskScore(),
                        e.getTimestamp()
                )
        ));

        return sw.toString();
    }
}
