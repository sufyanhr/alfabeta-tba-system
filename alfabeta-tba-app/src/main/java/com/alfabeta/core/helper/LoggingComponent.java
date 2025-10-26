package com.alfabeta.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * مكون تسجيل موحّد لكل أجزاء النظام (Controller / Service / Flow)
 * يسهّل التعامل مع logs ويوحّد التنسيق عبر كامل التطبيق.
 */
public interface LoggingComponent {
    default Logger log() { return LoggerFactory.getLogger(getClass()); }
    default void debug(String msg, Object... args) { log().debug(msg, args); }
    default void info(String msg, Object... args) { log().info(msg, args); }
    default void warn(String msg, Object... args) { log().warn(msg, args); }
    default void error(String msg, Object... args) { log().error(msg, args); }
    default void error(String msg, Throwable t, Object... args) { log().error(msg, t); }
}
