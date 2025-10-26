package com.alfabeta.core.helper;
/**
 * Tuple بسيط يحتوي على قيمتين.
 */
public record Tuple<A, B>(A first, B second) {
    public static <A, B> Tuple<A, B> of(A a, B b) { return new Tuple<>(a, b); }
}
