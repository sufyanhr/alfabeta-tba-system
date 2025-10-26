package com.alfabeta.core.helper;

/**
 * يُستخدم لتوليد وصف مقروء (readable) للكائنات في النماذج أو الواجهات.
 */
public interface ReadableCode {

    default String toReadableString() {
        return this.toString();
    }

    default String readableId() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}