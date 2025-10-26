package com.alfabeta.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when authentication or authorization fails.
 */
public class UnauthorizedException extends HttpStatusException {

    private static final long serialVersionUID = 3L;

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "Unauthorized access");
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, cause);
    }
}
