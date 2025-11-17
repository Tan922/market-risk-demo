package com.example.marketrisk.kafka.processor;
import com.example.marketrisk.model.AlertEvent;
import com.example.marketrisk.model.RiskMetric;
import org.springframework.stereotype.Component;

@Component
public class RiskAlertGenerationProcessor {
    public AlertEvent generateAlerts(RiskMetric data) {
        AlertEvent alert = new AlertEvent();
        alert.setSymbol(data.getSymbol());
        alert.setAlertType("HIGH_RISK");
        alert.setMessage(createAlertMessage(data));
        alert.setTimestamp(System.currentTimeMillis());
        return alert;
    }

    private String createAlertMessage(RiskMetric data) {
        return String.format("High risk detected! ID: %s, Score: %.2f", data.getSymbol(), data.getVar99().floatValue());
    }
}
