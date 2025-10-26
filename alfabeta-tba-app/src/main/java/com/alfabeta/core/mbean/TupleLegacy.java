package com.alfabeta.core.mbean;

import reactor.util.function.*;
import java.util.*;

/**
 * TupleLegacy class — backward compatible with Openkoda flows.
 * Supports dynamic tuple creation (1..8 elements) and Reactor tuples (Tuple2, Tuple3, etc.)
 *
 * @author Arkadiusz
 * @modifiedBy Alfabeta Team
 */
public class TupleLegacy {

    private final Object[] list;

    // === Constructors ===
    public TupleLegacy(Object[] args) { this.list = args; }
    public TupleLegacy(Object a1) { this.list = new Object[]{a1}; }
    public TupleLegacy(Object a1, Object a2) { this.list = new Object[]{a1, a2}; }
    public TupleLegacy(Object a1, Object a2, Object a3) { this.list = new Object[]{a1, a2, a3}; }
    public TupleLegacy(Object a1, Object a2, Object a3, Object a4) { this.list = new Object[]{a1, a2, a3, a4}; }
    public TupleLegacy(Object a1, Object a2, Object a3, Object a4, Object a5) { this.list = new Object[]{a1, a2, a3, a4, a5}; }
    public TupleLegacy(Object a1, Object a2, Object a3, Object a4, Object a5, Object a6) { this.list = new Object[]{a1, a2, a3, a4, a5, a6}; }
    public TupleLegacy(Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7) { this.list = new Object[]{a1, a2, a3, a4, a5, a6, a7}; }
    public TupleLegacy(Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7, Object a8) { this.list = new Object[]{a1, a2, a3, a4, a5, a6, a7, a8}; }

    // === Generic Accessors ===
    @SuppressWarnings("unchecked")
    public <T> T v(Class<T> c, int index) {
        return (T) list[index];
    }

    // === Common Key-Value Aliases ===
    public Object getKey() { return v0(); }
    public Object getValue() { return v1(); }

    // === Direct Access ===
    public Object v0() { return list[0]; }
    public Object v1() { return list[1]; }
    public Object v2() { return list.length > 2 ? list[2] : null; }
    public Object v3() { return list.length > 3 ? list[3] : null; }
    public Object v4() { return list.length > 4 ? list[4] : null; }
    public Object v5() { return list.length > 5 ? list[5] : null; }
    public Object v6() { return list.length > 6 ? list[6] : null; }
    public Object v7() { return list.length > 7 ? list[7] : null; }

    // === Reactor Tuple Conversion ===
    public Tuple2 t2() { return Tuples.of(v0(), v1()); }
    public Tuple3 t3() { return Tuples.of(v0(), v1(), v2()); }
    public Tuple4 t4() { return Tuples.of(v0(), v1(), v2(), v3()); }
    public Tuple5 t5() { return Tuples.of(v0(), v1(), v2(), v3(), v4()); }
    public Tuple6 t6() { return Tuples.of(v0(), v1(), v2(), v3(), v4(), v5()); }
    public Tuple7 t7() { return Tuples.of(v0(), v1(), v2(), v3(), v4(), v5(), v6()); }
    public Tuple8 t8() { return Tuples.of(v0(), v1(), v2(), v3(), v4(), v5(), v6(), v7()); }

    // === Collection Collector Support ===
    public static TupleLegacy collect(Collection<TupleLegacy> tuples, CollectorType... collectors) {
        Collection<?>[] result = new Collection[collectors.length];
        int size = tuples.size();

        for (int k = 0; k < collectors.length; k++) {
            switch (collectors[k]) {
                case LIST -> result[k] = new ArrayList<>(size);
                case SET -> result[k] = new HashSet<>();
            }
        }

        for (TupleLegacy t : tuples) {
            for (int k = 0; k < collectors.length; k++) {
                ((Collection<Object>) result[k]).add(t.v(Object.class, k));
            }
        }

        return new TupleLegacy(result);
    }

    public enum CollectorType { LIST, SET }

    // === Utility ===
    public int size() { return list.length; }

    public List<Object> toList() { return Arrays.asList(list); }

    @Override
    public String toString() {
        return "TupleLegacy" + Arrays.toString(list);
    }
}
