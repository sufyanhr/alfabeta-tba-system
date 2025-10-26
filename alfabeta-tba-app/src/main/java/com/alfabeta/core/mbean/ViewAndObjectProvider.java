package com.alfabeta.core.mbean;

import java.util.function.Supplier;

/**
 * Provides objects for UI rendering or lazy instantiation in views.
 */
public class ViewAndObjectProvider<T> {

    private final Supplier<T> supplier;

    public ViewAndObjectProvider(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        return supplier.get();
    }

    public static <T> ViewAndObjectProvider<T> of(Supplier<T> supplier) {
        return new ViewAndObjectProvider<>(supplier);
    }
}
