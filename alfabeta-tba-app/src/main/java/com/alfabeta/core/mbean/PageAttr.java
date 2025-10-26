package com.alfabeta.core.mbean;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Represents a typed key for page or model attributes.
 * Used as a type-safe reference instead of raw string keys.
 *
 * @param <T> Type of value stored in the attribute
 */
public class PageAttr<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String key;
    private final Supplier<T> defaultSupplier;

    /**
     * Constructor with only a key
     */
    public PageAttr(String key) {
        this.key = key;
        this.defaultSupplier = null;
    }

    /**
     * Constructor with a key and a default value supplier
     */
    public PageAttr(String key, Supplier<T> defaultSupplier) {
        this.key = key;
        this.defaultSupplier = defaultSupplier;
    }

    public String key() {
        return key;
    }

    /**
     * Returns a new instance of the default value (if available)
     */
    public T getDefaultValue() {
        return defaultSupplier != null ? defaultSupplier.get() : null;
    }

    @Override
    public String toString() {
        return "PageAttr{" +
                "key='" + key + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PageAttr<?> other)) return false;
        return key.equals(other.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
