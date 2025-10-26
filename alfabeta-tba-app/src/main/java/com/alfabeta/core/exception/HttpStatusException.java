package com.alfabeta.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.Serial;

/**
 * Base exception for all application-specific HTTP errors.
 * All custom exceptions should extend this class.
 */
public abstract class HttpStatusException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    protected final HttpStatus status;

    public HttpStatusException(HttpStatus status) {
        super(status.getReasonPhrase());
        this.status = status;
    }

    public HttpStatusException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatusException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public HttpStatusException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public ResponseEntity<String> build() {
        return ResponseEntity.status(status).body(getMessage());
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "HttpStatusException{" +
                "status=" + status.value() +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
