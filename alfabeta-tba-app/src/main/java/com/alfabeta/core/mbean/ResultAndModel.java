package com.alfabeta.core.mbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Generic container class that represents both the result (business outcome)
 * and its associated model/context.
 *
 * Designed for usage within Flow pipelines, service layers, and API responses.
 *
 * @param <I> Input or domain entity
 * @param <CP> Context Provider (service layer or dependencies)
 *
 * @author Alfabeta
 * @since 2025-10
 */
public class ResultAndModel<I, CP> implements Serializable {

    private static final long serialVersionUID = 2L;

    /** The business or domain result */
    private I result;

    /** The data model or attributes associated with the result */
    private Map<String, Object> model;

    /** The service or context provider */
    private CP services;

    /** The current status of this result */
    private ResultStatus status = ResultStatus.SUCCESS;

    /** Optional message (success, warning, or error description) */
    private String message;

    /** Optional error object (for exception tracking) */
    private Throwable error;

    // --------------------------------------------------------------------------------------------
    // Constructors
    // --------------------------------------------------------------------------------------------

    public ResultAndModel() {
        this.model = new HashMap<>();
    }

    public ResultAndModel(I result, CP services) {
        this();
        this.result = result;
        this.services = services;
    }

    public ResultAndModel(I result, CP services, ResultStatus status, String message) {
        this(result, services);
        this.status = status;
        this.message = message;
    }

    // --------------------------------------------------------------------------------------------
    // Builder-like methods (Fluent API)
    // --------------------------------------------------------------------------------------------

    public static <I, CP> ResultAndModel<I, CP> of(I result, CP services) {
        return new ResultAndModel<>(result, services);
    }

    public static <I, CP> ResultAndModel<I, CP> failure(CP services, String message, Throwable ex) {
        return new ResultAndModel<I, CP>()
                .withServices(services)
                .withStatus(ResultStatus.FAILURE)
                .withMessage(message)
                .withError(ex);
    }

    public ResultAndModel<I, CP> withResult(I result) {
        this.result = result;
        return this;
    }

    public ResultAndModel<I, CP> withServices(CP services) {
        this.services = services;
        return this;
    }

    public ResultAndModel<I, CP> withModel(Map<String, Object> model) {
        this.model = model;
        return this;
    }

    public ResultAndModel<I, CP> addAttribute(String key, Object value) {
        if (model == null) {
            model = new HashMap<>();
        }
        model.put(key, value);
        return this;
    }

    public ResultAndModel<I, CP> withStatus(ResultStatus status) {
        this.status = status;
        return this;
    }

    public ResultAndModel<I, CP> withMessage(String message) {
        this.message = message;
        return this;
    }

    public ResultAndModel<I, CP> withError(Throwable error) {
        this.error = error;
        return this;
    }

    public ResultAndModel<I, CP> markSuccess(String message) {
        this.status = ResultStatus.SUCCESS;
        this.message = message;
        this.error = null;
        return this;
    }

    public ResultAndModel<I, CP> markFailed(String message, Throwable error) {
        this.status = ResultStatus.FAILURE;
        this.message = message;
        this.error = error;
        return this;
    }

    public ResultAndModel<I, CP> markWarning(String message) {
        this.status = ResultStatus.WARNING;
        this.message = message;
        return this;
    }

    public ResultAndModel<I, CP> markInfo(String message) {
        this.status = ResultStatus.INFO;
        this.message = message;
        return this;
    }

    // --------------------------------------------------------------------------------------------
    // Getters
    // --------------------------------------------------------------------------------------------

    public I getResult() {
        return result;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public CP getServices() {
        return services;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status == ResultStatus.SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public Optional<Throwable> getError() {
        return Optional.ofNullable(error);
    }

    public Object getAttribute(String key) {
        return model != null ? model.get(key) : null;
    }

    // --------------------------------------------------------------------------------------------
    // Utilities
    // --------------------------------------------------------------------------------------------

    public boolean hasError() {
        return error != null;
    }

    public boolean hasAttribute(String key) {
        return model != null && model.containsKey(key);
    }

    @Override
    public String toString() {
        return "ResultAndModel{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", model=" + model +
                ", error=" + (error != null ? error.getMessage() : "null") +
                '}';
    }
}
