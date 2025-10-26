package com.alfabeta.core.helper;
/**
 * أدوات مساعدة للكائنات التي تنفذ واجهة ReadableCode.
 */

public class ReadableCodeUtil {
    public static String toReadable(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof ReadableCode rc) return rc.toReadableString();
        return obj.toString();
    }

    public static interface ReadableCode {
        default String toReadableString() { return this.toString(); }
        default String readableId() { return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()); }
    }
}
