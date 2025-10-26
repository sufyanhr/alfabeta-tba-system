package com.alfabeta.core.exception;

public class JsFlowExecutionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JsFlowExecutionException(String message) {
        super(message);
    }

    public JsFlowExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
