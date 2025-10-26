package com.alfabeta.core.flow;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PageModelMap extends HashMap<String,Object> {

    public PageModelMap() {
        super();
    }

    public PageModelMap(Map<String,Object> m) {
        super(m == null ? new HashMap<>() : new HashMap<>(m));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(PageAttr<T> attr) {
        return (T) get(attr.getName());
    }

    public <T> PageModelMap put(PageAttr<T> attr, T value) {
        put(attr.getName(), value);
        return this;
    }

    public boolean has(PageAttr<?> attr) {
        return containsKey(attr.getName());
    }

    public Map<String,Object> getAsMap(PageAttr<?>... attrs) {
        Map<String,Object> out = new HashMap<>();
        for (PageAttr<?> a : attrs) {
            if (containsKey(a.getName())) out.put(a.getName(), get(a.getName()));
        }
        return out;
    }

    public Map<String,Object> asMap() {
        return this;
    }

    @Override
    public String toString() {
        return "PageModelMap{" + super.toString() + "}";
    }
}
