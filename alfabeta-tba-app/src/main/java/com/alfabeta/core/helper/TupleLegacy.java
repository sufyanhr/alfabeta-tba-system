package com.alfabeta.core.helper;
/**
 * نسخة قديمة لتوافق بعض المكونات القديمة في Openkoda.
 */
public class TupleLegacy<X, Y> {
    private final X first;
    private final Y second;
    public TupleLegacy(X first, Y second) { this.first = first; this.second = second; }
    public X getFirst() { return first; }
    public Y getSecond() { return second; }
    @Override public String toString() { return "(" + first + ", " + second + ")"; }
}
