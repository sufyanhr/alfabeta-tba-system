package com.alfabeta.core.flow;

import com.alfabeta.core.flow.mbean.LoggingEntriesStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.Assert;
import org.springframework.util.ResizableByteArrayOutputStream;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Advanced logging interface integrated with MBean monitoring and Flow system.
 *
 * @author
 *   Original: Arkadiusz Drysch (Stratoflow)
 *   Modified by: Alfabeta Team (2025)
 */
public interface LoggingComponent {

    // Thread-safe collections
    Map<Class<?>, Logger> loggers = new ConcurrentHashMap<>();
    List<Class<?>> availableLoggers = new CopyOnWriteArrayList<>();
    Set<Class<?>> debugLoggers = ConcurrentHashMap.newKeySet();

    // Unified debug logger and stack
    Logger debugLogger = LoggerFactory.getLogger("jmxDebug");
    LoggingEntriesStack<Date> debugStack = new LoggingEntriesStack<>(500);

    /**
     * Retrieves or creates a logger for the current class.
     */
    default Logger getLogger(boolean createIfNotExists) {
        return loggers.computeIfAbsent(getClass(), cls -> {
            if (createIfNotExists) {
                Logger l = LoggerFactory.getLogger(cls);
                availableLoggers.add(cls);
                return l;
            }
            return null;
        });
    }

    default Logger getLogger() {
        return getLogger(true);
    }

    /** Debug logging */
    default void debug(String format, Object... arguments) {
        Logger l = getLogger();
        l.debug(format, arguments);
        if (isDebugLogger()) logToDebugStack(null, format, arguments);
    }

    /** Trace logging */
    default void trace(String format, Object... arguments) {
        Logger l = getLogger();
        l.trace(format, arguments);
        if (isDebugLogger()) logToDebugStack(null, format, arguments);
    }

    /** Info logging */
    default void info(String format, Object... arguments) {
        getLogger().info(format, arguments);
    }

    /** Warning logging */
    default void warn(String format, Object... arguments) {
        getLogger().warn(format, arguments);
        logToDebugStack(null, format, arguments);
    }

    default void warn(String message, Throwable throwable) {
        getLogger().warn(message, throwable);
        logToDebugStack(throwable, message);
    }

    /** Error logging */
    default void error(String format, Object... arguments) {
        getLogger().error(format, arguments);
        logToDebugStack(null, format, arguments);
    }

    default void error(Throwable throwable, String format, Object... arguments) {
        getLogger().error(format, arguments);
        getLogger().error("[error] cause:", throwable);
        logToDebugStack(throwable, format, arguments);
    }

    default void error(String message, Throwable throwable) {
        getLogger().error(message, throwable);
        logToDebugStack(throwable, message);
    }

    /** Formats a message manually */
    default String formatMessage(String format, Object... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        return ft.getMessage();
    }

    /** Pushes log entries into MBean debug stack */
    default void logToDebugStack(Throwable t, String message, Object... arguments) {
        try (ResizableByteArrayOutputStream buffer = new ResizableByteArrayOutputStream(8 * 128);
             PrintStream ps = new PrintStream(buffer)) {

            message = formatMessage(message, arguments);
            if (t != null) {
                ps.append(message).append("\n");
                t.printStackTrace(ps);
                message = buffer.toString();
            }

            debugLogger.debug(message);
            debugStack.log(new Date(), getClass().getSimpleName() + " - " + message);
        } catch (Exception e) {
            debugLogger.error("Failed to log to debug stack", e);
        }
    }

    /** Check if current class is in debug list */
    default boolean isDebugLogger() {
        return debugLoggers.contains(getClass());
    }

    /** Accessor for stored debug entries */
    default Map<Date, String> getDebugEntries() {
        return debugStack;
    }

    /** Assert helpers */
    default void notNull(Object o) {
        Assert.notNull(o, "Object must not be null");
    }

    default void isTrue(Boolean b) {
        Assert.isTrue(Boolean.TRUE.equals(b), "Condition must be true");
    }

    /** Logger lists for management interfaces */
    default List<Class<?>> getAvailableLoggers() {
        return availableLoggers;
    }

    default Set<Class<?>> getDebugLoggers() {
        return debugLoggers;
    }
}
