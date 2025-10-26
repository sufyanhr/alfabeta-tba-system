package com.alfabeta.core.exception;

import com.alfabeta.service.system.ErrorTrackerService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * ✅ Global Exception Handler for Alfabeta System.
 * Centralized error response builder + DB logging via ErrorTrackerService.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ErrorTrackerService errorTrackerService; // ✅ inject service properly

    /**
     * Handles all custom HttpStatusExceptions (Validation, Unauthorized, etc.)
     */
    @ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<ApiExceptionResponse> handleHttpStatusException(
            HttpStatusException ex,
            WebRequest request,
            HttpServletRequest servletRequest
    ) {
        log.warn("[{}] {}", ex.getStatus(), ex.getMessage());

        String errorCode = switch (ex.getStatus()) {
            case UNAUTHORIZED -> ErrorCodes.ERR_401_UNAUTHORIZED;
            case FORBIDDEN -> ErrorCodes.ERR_403_FORBIDDEN;
            case NOT_FOUND -> ErrorCodes.ERR_404_NOT_FOUND;
            case CONFLICT -> ErrorCodes.ERR_409_CONFLICT;
            default -> ErrorCodes.ERR_500_INTERNAL_ERROR;
        };

        // 🔹 Save the exception in the DB
        try {
            errorTrackerService.trackException(ex, servletRequest, errorCode);
        } catch (Exception loggingError) {
            log.error("Failed to save error in DB: {}", loggingError.getMessage());
        }

        ApiExceptionResponse body = ApiExceptionResponse.of(
                ex.getStatus(),
                ex.getMessage(),
                errorCode,
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(body, ex.getStatus());
    }

    /**
     * Handles all unexpected runtime/system exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleGenericException(
            Exception ex,
            WebRequest request,
            HttpServletRequest servletRequest
    ) {
        log.error("[500] Unexpected error: {}", ex.getMessage(), ex);

        String errorCode = ErrorCodes.ERR_500_INTERNAL_ERROR;

        // 🔹 Try logging the error
        try {
            errorTrackerService.trackException(ex, servletRequest, errorCode);
        } catch (Exception e2) {
            log.error("Error while tracking exception: {}", e2.getMessage());
        }

        ApiExceptionResponse body = ApiExceptionResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.",
                errorCode,
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
