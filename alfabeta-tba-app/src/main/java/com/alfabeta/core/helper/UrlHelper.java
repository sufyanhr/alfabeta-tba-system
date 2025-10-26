package com.alfabeta.core.helper;
/**
 * إنشاء وتنظيف الروابط بشكل آمن.
 */

public class UrlHelper {
    public static String normalize(String url) {
        if (url == null) return "/";
        String clean = url.trim().replaceAll("//+", "/");
        return clean.startsWith("/") ? clean : "/" + clean;
    }
    public static String append(String base, String path) {
        if (base == null) base = "";
        if (path == null) path = "";
        return normalize(base + "/" + path);
    }
}
