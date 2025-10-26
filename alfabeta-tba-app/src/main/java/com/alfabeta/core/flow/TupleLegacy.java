package com.alfabeta.core.flow;

import reactor.util.function.*;

import java.util.Arrays;

public class TupleLegacy {
    private final Object[] list;
    public TupleLegacy(Object... args) { this.list = args == null ? new Object[0] : args; }
    public Object v0(){ return list.length>0 ? list[0] : null; }
    public Object v1(){ return list.length>1 ? list[1] : null; }
    // ... المزيد إن احتجت

    public Tuple2<Object,Object> t2() {
        return Tuples.of(v0(), v1());
    }

    @Override
    public String toString() {
        return "TupleLegacy" + Arrays.toString(list);
    }
}
