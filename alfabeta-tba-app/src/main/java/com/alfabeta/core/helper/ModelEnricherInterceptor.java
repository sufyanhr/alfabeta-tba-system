package com.alfabeta.core.helper;

import com.alfabeta.core.flow.PageModelMap;

public class ModelEnricherInterceptor {
    public static void enrich(PageModelMap map) {
        map.put("timestamp", System.currentTimeMillis());
    }
}
