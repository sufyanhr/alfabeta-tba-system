package com.alfabeta.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Represents an unexpected internal server error (HTTP 500).
 */
public class ServerErrorException extends HttpStatusException {

    private static final long serialVersionUID = 5L;

    public ServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    public ServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public ServerErrorException(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }
}
