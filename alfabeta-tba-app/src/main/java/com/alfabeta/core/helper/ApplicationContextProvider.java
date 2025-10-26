package com.alfabeta.core.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * يسمح بالوصول إلى Beans في Spring خارج السياق التقليدي (مثلاً داخل كود static).
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override public void setApplicationContext(ApplicationContext ctx) throws BeansException { context = ctx; }
    public static <T> T getBean(Class<T> clazz) { return context.getBean(clazz); }
    public static Object getBean(String name) { return context.getBean(name); }
    public static ApplicationContext getContext() { return context; }
}
