package com.alfabeta.core.mbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Abstract logger base providing standard log and statistics integration.
 */
@Component
public abstract class LoggingComponent {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    @Lazy
    private StatisticsMBean statisticsMBean;

    protected void debug(String msg, Object... args) {
        log.debug(msg, args);
    }

    protected void info(String msg, Object... args) {
        log.info(msg, args);
    }

    protected void warn(String msg, Object... args) {
        log.warn(msg, args);
    }

    protected void error(String msg, Throwable t, Object... args) {
        log.error(msg, t);
        if (statisticsMBean != null) {
            statisticsMBean.recordError(java.time.Instant.now(), t);
        }
    }

    protected LoggingEntriesStack startLogContext(String contextName) {
        return new LoggingEntriesStack(contextName);
    }
}
