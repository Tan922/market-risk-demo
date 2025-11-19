package com.example.marketrisk.kafka.processor;

import com.example.marketrisk.model.AlertEvent;
import com.example.marketrisk.model.MarketRiskSnapshot;
import org.apache.kafka.streams.processor.api.ContextualProcessor;
import org.apache.kafka.streams.processor.api.Record;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class RiskAlertProcessor extends ContextualProcessor<String, MarketRiskSnapshot, String, AlertEvent> {

    private final double volatilityThreshold;
    private final BigDecimal dailyPnlDropThreshold;
    private final BigDecimal var99Threshold;

    public RiskAlertProcessor() {
        this.volatilityThreshold = 0.08;
        this.dailyPnlDropThreshold = BigDecimal.valueOf(-50000);
        this.var99Threshold = BigDecimal.valueOf(100000);
    }

    @Override
    public void process(Record<String, MarketRiskSnapshot> record) {
        MarketRiskSnapshot s = record.value();
        if (s == null) return;

        if (s.getVolatility30() != null && s.getVolatility30().doubleValue() > volatilityThreshold) {
            AlertEvent a = new AlertEvent();
            a.setSymbol(s.getSymbol());
            a.setAlertType("VOLATILITY_SPIKE");
            a.setMessage("30-day volatility " + s.getVolatility30() + " > " + volatilityThreshold);
            a.setTimestamp(System.currentTimeMillis());
            context().forward(record.withValue(a));
            return;
        }

        if (s.getDailyPnl() != null && s.getDailyPnl().compareTo(dailyPnlDropThreshold) < 0) {
            AlertEvent a = new AlertEvent();
            a.setSymbol(s.getSymbol());
            a.setAlertType("DAILY_PNL_DROP");
            a.setMessage("Daily PnL " + s.getDailyPnl() + " < " + dailyPnlDropThreshold);
            a.setTimestamp(System.currentTimeMillis());
            context().forward(record.withValue(a));
            return;
        }

        if (s.getVar1at99() != null && s.getVar1at99().compareTo(var99Threshold) > 0) {
            AlertEvent a = new AlertEvent();
            a.setSymbol(s.getSymbol());
            a.setAlertType("VAR_BREACH");
            a.setMessage("1-day 99% VaR " + s.getVar1at99() + " > " + var99Threshold);
            a.setTimestamp(System.currentTimeMillis());
            context().forward(record.withValue(a));
        }
    }
}
