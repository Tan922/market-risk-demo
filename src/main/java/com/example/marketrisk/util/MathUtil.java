package com.example.marketrisk.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public final class MathUtil {

    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);

    private MathUtil() {}

    public static BigDecimal movingAverage(List<BigDecimal> prices) {
        if (prices == null || prices.isEmpty())
            return BigDecimal.ZERO;

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal p : prices) {
            sum = sum.add(p, MC);
        }
        return sum.divide(BigDecimal.valueOf(prices.size()), MC);
    }

    public static BigDecimal standardDeviation(List<BigDecimal> prices) {
        if (prices == null || prices.size() < 2)
            return BigDecimal.ZERO;

        BigDecimal mean = movingAverage(prices);

        BigDecimal varianceSum = BigDecimal.ZERO;
        for (BigDecimal p : prices) {
            BigDecimal diff = p.subtract(mean, MC);
            varianceSum = varianceSum.add(diff.pow(2, MC), MC);
        }

        BigDecimal variance = varianceSum.divide(
                BigDecimal.valueOf(prices.size() - 1), MC);

        return sqrt(variance);
    }

    public static BigDecimal percentageChange(BigDecimal oldVal, BigDecimal newVal) {
        if (oldVal == null || oldVal.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;

        return newVal.subtract(oldVal)
                .divide(oldVal, MC)
                .multiply(BigDecimal.valueOf(100), MC);
    }

    public static BigDecimal sqrt(BigDecimal value) {
        BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
        return x.round(MC);
    }
}
