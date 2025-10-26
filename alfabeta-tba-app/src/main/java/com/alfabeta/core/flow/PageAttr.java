package com.alfabeta.core.flow;

import java.util.Objects;
import java.util.function.Supplier;

public final class PageAttr<T> {

    private final String name;
    private final Supplier<T> defaultSupplier;

    public PageAttr(String name) {
        this(name, null);
    }

    public PageAttr(String name, Supplier<T> defaultSupplier) {
        this.name = Objects.requireNonNull(name, "name");
        this.defaultSupplier = defaultSupplier;
    }

    public String getName() {
        return name;
    }

    public T defaultValue() {
        return defaultSupplier == null ? null : defaultSupplier.get();
    }

    @Override
    public String toString() {
        return "PageAttr{" + name + '}';
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PageAttr && ((PageAttr)o).name.equals(this.name);
    }

    // helper to look up by name if you implement registry later
    public static <T> PageAttr<T> of(String name) {
        return new PageAttr<>(name);
    }
}
