package com.alfabeta.core.helper;

public class NameHelper {

    public static String normalize(String name) {
        return name == null ? null : name.trim().replaceAll("\\s+", "_").toLowerCase();
    }

    public static boolean isValid(String name) {
        return name != null && name.matches("[a-zA-Z0-9_\\-]+");
    }

    public static String capitalize(String name) {
        if (name == null || name.isEmpty()) return name;
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
