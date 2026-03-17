package org.jrsa.agent.performance;

import java.util.concurrent.atomic.AtomicLong;

public final class EventRateLimiter {

    private static final long MAX_EVENTS_PER_SECOND = 100;

    private static final AtomicLong counter = new AtomicLong(0);
    private static volatile long currentSecond =
            System.currentTimeMillis() / 1000;

    private EventRateLimiter() {}

    public static boolean allow() {

        long nowSecond = System.currentTimeMillis() / 1000;

        if (nowSecond != currentSecond) {
            currentSecond = nowSecond;
            counter.set(0);
        }

        return counter.incrementAndGet() <= MAX_EVENTS_PER_SECOND;
    }
}