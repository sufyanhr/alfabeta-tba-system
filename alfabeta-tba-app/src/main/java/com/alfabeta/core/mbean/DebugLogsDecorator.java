package com.alfabeta.core.mbean;

import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Decorator class that formats and outputs logs for debugging.
 * Can be integrated with future dashboards or log analysis tools.
 */
@Component
public class DebugLogsDecorator {

    /**
     * Prints a formatted log entry to console.
     */
    public void print(String context, String message) {
        System.out.printf("[%s] [%s] %s%n", Instant.now(), context, message);
    }

    /**
     * Prints error details in a structured way.
     */
    public void printError(String context, Throwable t) {
        System.err.printf("[%s] [ERROR] [%s] %s: %s%n",
                Instant.now(),
                context,
                t.getClass().getSimpleName(),
                t.getMessage());
    }

    /**
     * Prints the result of a completed context log.
     */
    public void printSummary(LoggingEntriesStack stack) {
        System.out.println("\n======= LOG SUMMARY: " + stack.getContextName() + " =======");
        stack.getEntries().forEach(System.out::println);
        System.out.printf("Duration: %d ms%n", stack.getDurationMs());
        System.out.println("========================================\n");
    }
}
