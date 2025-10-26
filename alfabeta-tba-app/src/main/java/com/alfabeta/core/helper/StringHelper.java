package com.alfabeta.core.helper;
/**
 * أدوات معالجة النصوص والتحقق منها.
 */
public class StringHelper {
    public static boolean isEmpty(String str) { return str == null || str.trim().isEmpty(); }
    public static boolean isNotEmpty(String str) { return !isEmpty(str); }
    public static String safe(String str) { return str == null ? "" : str.trim(); }
    public static String nullIfEmpty(String str) { return isEmpty(str) ? null : str.trim(); }
    public static String capitalize(String str) {
        if (isEmpty(str)) return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    public static String truncate(String str, int length) {
        if (str == null || str.length() <= length) return str;
        return str.substring(0, length) + "...";
    }
}
