package Semestr_2.Lista_9_L;

import java.time.Duration;
import java.time.Instant;

public class Timer {
    private Instant startPoint;
    private Instant stopPoint;

    public void start() {
        startPoint = Instant.now();
        stopPoint = null;
    }

    public void stop() {
        stopPoint = Instant.now();
    }

    public void reset() {
        startPoint = null;
        stopPoint = null;
    }

    private Duration duration() {
        return (startPoint == null || stopPoint == null) ? Duration.ZERO : Duration.between(startPoint, stopPoint);
    }

    public long durationNanos() {
        return duration().toNanos();
    }
}
