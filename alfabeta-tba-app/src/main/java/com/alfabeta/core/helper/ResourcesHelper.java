package com.alfabeta.core.helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * تحميل الملفات من الموارد (resources/) بشكل آمن وسريع.
 */

public class ResourcesHelper {
    public static String readResource(String path) {
        try {
            Resource resource = new ClassPathResource(path);
            byte[] bytes = resource.getInputStream().readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + path, e);
        }
    }
}
