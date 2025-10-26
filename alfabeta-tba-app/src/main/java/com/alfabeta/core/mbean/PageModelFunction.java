package com.alfabeta.core.mbean;

import java.util.Map;
import java.util.function.Function;

/**
 * Represents a functional model transformation inside Flow.
 */
@FunctionalInterface
public interface PageModelFunction<I, O> extends Function<I, O> {

    @Override
    O apply(I input);

    default PageModelFunction<I, O> withLogging(String name) {
        return input -> {
            System.out.println("[Flow] Executing function: " + name + " with input: " + input);
            return this.apply(input);
        };
    }

    static <I> PageModelFunction<I, I> identity() {
        return i -> i;
    }
}
