package com.alfabeta.core.exception;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

/**
 * DTO for standardized JSON error responses used by GlobalExceptionHandler.
 */
public record ApiExceptionResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String code,   // 🔹 New: standardized error code (e.g., ERR_404_NOT_FOUND)
        String path
) {
    public static ApiExceptionResponse of(HttpStatus status, String message, String code, String path) {
        return new ApiExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                code,
                path
        );
    }

    public static ApiExceptionResponse of(HttpStatus status, String message, String path) {
        // fallback if no code is given
        return of(status, message, "ERR_" + status.value() + "_" + status.getReasonPhrase().toUpperCase().replace(" ", "_"), path);
    }
}
