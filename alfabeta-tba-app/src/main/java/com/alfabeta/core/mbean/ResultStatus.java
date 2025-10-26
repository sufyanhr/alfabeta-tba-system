package com.alfabeta.core.mbean;

import java.io.Serializable;

/**
 * Represents the general status of a ResultAndModel operation.
 * Used to provide richer result context instead of a simple boolean flag.
 *
 * @author Alfabeta
 * @since 2025-10
 */
public enum ResultStatus implements Serializable {

    /**
     * Operation completed successfully.
     */
    SUCCESS("Operation completed successfully"),

    /**
     * Operation failed due to business or system error.
     */
    FAILURE("Operation failed"),

    /**
     * Operation completed with warnings (partial success).
     */
    WARNING("Operation completed with warnings"),

    /**
     * Informational result (no actual state change).
     */
    INFO("Informational result"),

    /**
     * Operation is pending or still being processed.
     */
    PENDING("Operation pending");

    private final String defaultMessage;

    ResultStatus(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String toString() {
        return name() + " - " + defaultMessage;
    }
}
