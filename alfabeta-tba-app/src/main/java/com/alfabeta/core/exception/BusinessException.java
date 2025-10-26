package com.alfabeta.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Represents business logic errors that are not caused by technical failures,
 * but by domain rules. For example:
 * - Trying to register an already existing user
 * - Attempting to process a closed insurance claim
 * - Submitting a form with invalid state
 *
 * These are returned as HTTP 409 (Conflict) by default.
 */
public class BusinessException extends HttpStatusException {

    private static final long serialVersionUID = 10L;

    public BusinessException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

    public BusinessException(String message, Throwable cause) {
        super(HttpStatus.CONFLICT, message, cause);
    }
}
