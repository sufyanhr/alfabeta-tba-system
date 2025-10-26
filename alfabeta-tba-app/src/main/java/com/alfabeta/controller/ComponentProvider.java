

package com.alfabeta.controller;

import com.alfabeta.controller.common.PageAttributes;
import com.alfabeta.core.flow.TransactionalExecutor;
import com.alfabeta.core.helper.Messages;
import com.alfabeta.core.tracker.LoggingComponentWithRequestId;
import com.alfabeta.repository.Repositories;
import com.alfabeta.service.Services;
import jakarta.inject.Inject;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * <p>Class that aggregates all Services, Repositories and Controllers so that it can be extended by other
 * Spring components and reduce the need for injecting beans.</p>
 *
 * @author Arkadiusz Drysch (adrysch@stratoflow.com)
 * 
 */
public class ComponentProvider implements PageAttributes, LoggingComponentWithRequestId, InitializingBean {

    @Inject
    public Services services;

    @Inject
    public Repositories repositories;

    @Inject
    public Controllers controllers;

    @Inject
    public Messages messages;

    protected Supplier<TransactionalExecutor> transactional = () -> services.transactionalExecutor;

    public final static Map<String, Object> resources = new HashMap<>();
    private static boolean initialized = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(!initialized) {
            initialized = true;
            resources.put("services", services);
            resources.put("repositories", repositories);
        }
    }
}
