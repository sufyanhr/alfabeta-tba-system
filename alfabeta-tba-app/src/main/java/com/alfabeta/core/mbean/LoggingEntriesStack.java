package com.alfabeta.core.mbean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a stack or log of entries (typically for debugging or JS thread tracking).
 * Supports generic entry types (e.g., String, LogEntry, etc.)
 *
 * @param <E> Type of log entry
 *
 * @author Alfabeta
 * @since 2025-10
 */
public class LoggingEntriesStack<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<E> entries = new ArrayList<>();
    private final LocalDateTime createdAt = LocalDateTime.now();

    public void add(E entry) {
        entries.add(entry);
    }

    public void addAll(List<E> newEntries) {
        entries.addAll(newEntries);
    }

    public E getLast() {
        return entries.isEmpty() ? null : entries.get(entries.size() - 1);
    }

    public List<E> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public int size() {
        return entries.size();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void clear() {
        entries.clear();
    }

    @Override
    public String toString() {
        return "LoggingEntriesStack{" +
                "entries=" + entries +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setMaxEntries(int maxEntries) {

    }
}
