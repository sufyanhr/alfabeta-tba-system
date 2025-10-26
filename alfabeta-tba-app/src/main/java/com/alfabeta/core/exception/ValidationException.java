package com.alfabeta.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when validation fails for user input or business logic.
 */
public class ValidationException extends HttpStatusException {

    private static final long serialVersionUID = 2L;

    public ValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public ValidationException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }
}
