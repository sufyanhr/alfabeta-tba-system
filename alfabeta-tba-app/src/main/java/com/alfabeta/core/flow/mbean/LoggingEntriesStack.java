
package com.alfabeta.core.flow.mbean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe stack for storing recent log entries.
 * Used by {@link com.alfabeta.core.flow.LoggingComponent}
 * and exposed to JMX for live system monitoring.
 *
 * @param <K> Key type (usually Date or LocalDateTime)
 */
public class LoggingEntriesStack<K> extends LinkedHashMap<K, String> {

    private static final long serialVersionUID = 1L;

    private int maxEntries = 500;
    private final ReentrantLock lock = new ReentrantLock();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LoggingEntriesStack(int maxEntries) {
        super(maxEntries, 0.75f, true);
        this.maxEntries = maxEntries;
    }

    public LoggingEntriesStack() {
        super(500, 0.75f, true);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, String> eldest) {
        return size() > maxEntries;
    }

    public int getMaxEntries() {
        return maxEntries;
    }

    public void setMaxEntries(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    /**
     * Adds a new log entry in a thread-safe way.
     * If the same key exists, it will append the new message.
     */
    public void log(K key, String message) {
        lock.lock();
        try {
            String timestamp = formatKey(key);
            String existing = super.get(key);
            String combinedMessage = (existing != null)
                    ? existing + "\n" + message
                    : "[" + timestamp + "] " + message;
            super.put(key, combinedMessage);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Simplified log method using current timestamp as key.
     */
    public void log(String message) {
        log((K) LocalDateTime.now(), message);
    }

    /**
     * Returns all entries as a formatted string (useful for console or MBean view).
     */
    public String dump() {
        lock.lock();
        try {
            StringBuilder sb = new StringBuilder();
            this.forEach((k, v) ->
                    sb.append("[").append(formatKey(k)).append("] ")
                            .append(v).append("\n"));
            return sb.toString();
        } finally {
            lock.unlock();
        }
    }

    private String formatKey(K key) {
        if (key instanceof LocalDateTime ldt) {
            return DATE_FORMAT.format(ldt);
        } else if (key instanceof java.util.Date date) {
            return DATE_FORMAT.format(
                    date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        }
        return key.toString();
    }
}
