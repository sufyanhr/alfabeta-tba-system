package com.alfabeta.core.helper;

public class ModulesInterceptor {
    public static void intercept(String moduleName) {
        System.out.println("[Module Intercepted] " + moduleName);
    }
}
