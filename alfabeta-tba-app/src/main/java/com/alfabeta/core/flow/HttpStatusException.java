

package com.alfabeta.core.flow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class HttpStatusException extends RuntimeException {

    public final HttpStatus status;

    public HttpStatusException(HttpStatus status) {
        this.status = status;
    }
    public HttpStatusException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }
    public HttpStatusException(HttpStatus status, String message) {
        super(message);
        this.status = status;

    }

    public ResponseEntity build() {
        return ResponseEntity.status(status).build();
    }


}
