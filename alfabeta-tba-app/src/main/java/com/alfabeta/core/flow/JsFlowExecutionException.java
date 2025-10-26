

package com.alfabeta.core.flow;

public class JsFlowExecutionException extends RuntimeException {

    private final String code;
    private final String position;

    public JsFlowExecutionException(String message, String code, String position) {
        super(String.format("Error executing flow: %s\nat: %s\ncode:\n%s\n", message, position, code));
        this.code = code;
        this.position = position;
    }

    public JsFlowExecutionException(String message, Throwable cause) {
        super(String.format("Error executing flow: %s\nat: %s\ncode:\n%s\n", message, "N/A", "N/A"), cause);
        this.code = "N/A";
        this.position = "N/A";
    }
}
