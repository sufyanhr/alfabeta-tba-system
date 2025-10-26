package com.alfabeta.core.mbean;

import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracks execution statistics for system flows, transactions, and services.
 * Designed for monitoring and debugging.
 *
 * Compatible with Spring Boot 3 and Flow architecture.
 */
@Component
public class StatisticsMBean {

    private final AtomicLong totalExecutions = new AtomicLong(0);
    private final AtomicLong totalErrors = new AtomicLong(0);
    private final AtomicLong totalExecutionTimeMs = new AtomicLong(0);

    /**
     * Records the start time of an operation.
     */
    public Instant start() {
        return Instant.now();
    }

    /**
     * Records a successful execution.
     */
    public void recordSuccess(Instant startTime) {
        totalExecutions.incrementAndGet();
        totalExecutionTimeMs.addAndGet(Duration.between(startTime, Instant.now()).toMillis());
    }

    /**
     * Records an error event.
     */
    public void recordError(Instant startTime, Throwable ex) {
        totalErrors.incrementAndGet();
        totalExecutionTimeMs.addAndGet(Duration.between(startTime, Instant.now()).toMillis());
        System.err.println("[StatisticsMBean] Error recorded: " + ex.getMessage());
    }

    /**
     * Calculates average execution time.
     */
    public double getAverageExecutionTimeMs() {
        long executions = totalExecutions.get();
        return executions == 0 ? 0.0 : (double) totalExecutionTimeMs.get() / executions;
    }

    /**
     * Returns total number of executions.
     */
    public long getTotalExecutions() {
        return totalExecutions.get();
    }

    /**
     * Returns total number of recorded errors.
     */
    public long getTotalErrors() {
        return totalErrors.get();
    }

    /**
     * Prints formatted statistics (for debugging / admin panel).
     */
    public String report() {
        return """
               ====== SYSTEM STATISTICS ======
               Total Executions : %d
               Total Errors     : %d
               Avg Exec Time    : %.2f ms
               =================================
               """.formatted(
                getTotalExecutions(),
                getTotalErrors(),
                getAverageExecutionTimeMs()
        );
    }

    /**
     * Reset all counters (for admin or scheduled cleanup).
     */
    public void reset() {
        totalExecutions.set(0);
        totalErrors.set(0);
        totalExecutionTimeMs.set(0);
    }
}
