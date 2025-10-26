package com.alfabeta.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * أدوات تحويل الكائنات إلى JSON والعكس.
 */
public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static String to(Object object) {
        if (object == null) return "{}";
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"Failed to convert object to JSON\"}";
        }
    }

    public static <T> T from(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON parse error: " + e.getMessage(), e);
        }
    }
}
