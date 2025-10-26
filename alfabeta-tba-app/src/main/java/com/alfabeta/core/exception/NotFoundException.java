package com.alfabeta.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when a requested resource cannot be found.
 */
public class NotFoundException extends HttpStatusException {

    private static final long serialVersionUID = 4L;

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, "Resource not found");
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }
}
