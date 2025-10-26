package com.alfabeta.core.helper;

import org.springframework.core.env.Environment;

public class SpringProfilesHelper {
    public static boolean isActive(Environment env, String profile) {
        for (String p : env.getActiveProfiles()) {
            if (p.equalsIgnoreCase(profile)) return true;
        }
        return false;
    }
}