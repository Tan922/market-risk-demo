package com.example.marketrisk.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class TimeUtil {

    private TimeUtil() {}

    public static long now() {
        return System.currentTimeMillis();
    }

    public static long nowNano() {
        return System.nanoTime();
    }

    public static LocalDateTime toDateTime(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
        );
    }

    public static long toMillis(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
