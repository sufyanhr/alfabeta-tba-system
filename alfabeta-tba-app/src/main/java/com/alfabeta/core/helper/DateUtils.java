package com.alfabeta.core.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
/**
 * أدوات موحّدة للتعامل مع التواريخ.
 */
public class DateUtils {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String nowAsString() { return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN)); }
    public static String format(LocalDateTime date, String pattern) {
        if (date == null) return "";
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static LocalDateTime now() { return LocalDateTime.now(ZoneId.systemDefault()); }
}
