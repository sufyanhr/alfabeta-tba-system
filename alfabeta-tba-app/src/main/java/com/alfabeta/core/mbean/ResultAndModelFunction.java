package com.alfabeta.core.mbean;

import java.util.function.Function;

/**
 * Generic wrapper for ResultAndModel functional flows.
 */
@FunctionalInterface
public interface ResultAndModelFunction<I, CP, O> extends Function<ResultAndModel<I, CP>, O> {

    @Override
    O apply(ResultAndModel<I, CP> input);

    default <V> ResultAndModelFunction<I, CP, V> andThen(ResultAndModelFunction<O, CP, V> after) {
        return (ResultAndModel<I, CP> input) -> {
            O intermediate = this.apply(input);
            ResultAndModel<O, CP> newInput = new ResultAndModel<>(intermediate, input.getContext(), null, input.getParams());
            return after.apply(newInput);
        };
    }
}
