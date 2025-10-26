package com.alfabeta.core.mbean;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper map for storing model-related attributes or configurations.
 */
public class PageModelMap extends HashMap<String, Object> {

    public PageModelMap add(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public static PageModelMap of(String key, Object value) {
        PageModelMap map = new PageModelMap();
        map.put(key, value);
        return map;
    }

    public Map<String, Object> toMap() {
        return this;
    }
}
