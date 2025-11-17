package com.example.marketrisk.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Utility class for generating realistic price ticks.
 * Uses geometric Brownian motion approximation:
 *      S(t+1) = S(t) * (1 + drift + noise * volatility)
 */
public final class PriceGeneratorUtil {

    private static final Random RANDOM = new Random();
    private PriceGeneratorUtil() {}

    public static BigDecimal generateNextPrice(BigDecimal currentPrice) {
        double price = currentPrice.doubleValue();

        // Drift toward stability
        double drift = 0.0005; // small positive drift

        // Random movement (volatility)
        double shock = RANDOM.nextGaussian() * 0.01;  // 1% std deviation

        double next = price * (1 + drift + shock);

        if (next <= 0) next = price; // safety floor

        return BigDecimal.valueOf(next).setScale(4, RoundingMode.HALF_UP);
    }

    public static BigDecimal randomVolume() {
        int min = 50;
        int max = 5000;
        int volume = min + RANDOM.nextInt(max - min);
        return BigDecimal.valueOf(volume);
    }
}
