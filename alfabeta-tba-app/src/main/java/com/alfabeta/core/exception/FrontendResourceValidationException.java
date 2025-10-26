package com.alfabeta.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Validation error for frontend resources (forms, components, etc.).
 */
public class FrontendResourceValidationException extends HttpStatusException {

    private static final long serialVersionUID = 7L;
    private final boolean result;
    private final String suggestion;

    public FrontendResourceValidationException(boolean result, String suggestion) {
        super(HttpStatus.BAD_REQUEST, suggestion);
        this.result = result;
        this.suggestion = suggestion;
    }

    public boolean isResult() {
        return result;
    }

    public String getSuggestion() {
        return suggestion;
    }
}
