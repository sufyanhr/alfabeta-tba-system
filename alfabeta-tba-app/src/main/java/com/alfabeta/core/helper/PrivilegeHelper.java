package com.alfabeta.core.helper;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class for safely converting string values to enum constants.
 * Used by multiple services and form builders across the Alfabeta system.
 *
 * Example:
 *   PrivilegeType type = PrivilegeHelper.valueOfString(PrivilegeType.class, "ADMIN", PrivilegeType.USER);
 *
 * This method will never throw exceptions and will always return a valid enum constant.
 */
public class PrivilegeHelper {

    /**
     * Safely converts a string to an enum constant of the specified type.
     *
     * @param enumClass   Enum class to convert into (e.g., RoleType.class)
     * @param value       The string value to parse
     * @param defaultValue The fallback value if parsing fails
     * @param <E>         The Enum type
     * @return Enum constant if found, otherwise defaultValue
     */
    public static <E extends Enum<E>> E valueOfString(Class<E> enumClass, String value, E defaultValue) {
        if (enumClass == null || StringUtils.isBlank(value)) {
            return defaultValue;
        }
        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * Overloaded version that returns null instead of a default value.
     */
    public static <E extends Enum<E>> E valueOfString(Class<E> enumClass, String value) {
        return valueOfString(enumClass, value, null);
    }
}
