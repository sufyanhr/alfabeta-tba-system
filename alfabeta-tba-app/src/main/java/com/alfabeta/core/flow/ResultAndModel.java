package com.alfabeta.core.flow;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Holder for result of an operation and the page model (attributes).
 */
public class ResultAndModel<R, CP> {

    private R result;
    private final Map<String,Object> model;
    private ResultStatus status;

    // optional reference to control context (services)
    private CP services;

    public ResultAndModel() {
        this(null, new HashMap<>(), ResultStatus.OK, null);
    }

    public ResultAndModel(R result) {
        this(result, new HashMap<>(), ResultStatus.OK, null);
    }

    public ResultAndModel(R result, Map<String,Object> model, ResultStatus status, CP services) {
        this.result = result;
        this.model = model == null ? new HashMap<>() : model;
        this.status = status == null ? ResultStatus.OK : status;
        this.services = services;
    }

    public static <R, CP> ResultAndModel<R, CP> of(R result) {
        return new ResultAndModel<>(result);
    }

    public static <R, CP> ResultAndModel<R, CP> empty() {
        return new ResultAndModel<>();
    }

    public R getResult() {
        return result;
    }

    public ResultAndModel<R, CP> setResult(R result) {
        this.result = result;
        return this;
    }

    public Map<String,Object> getModel() {
        return model;
    }

    public Object get(String key) {
        return model.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        Object v = model.get(key);
        if (v == null) return null;
        if (type.isInstance(v)) {
            return type.cast(v);
        }
        throw new ClassCastException("Attribute " + key + " is not of type " + type.getName());
    }

    public ResultAndModel<R, CP> put(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public ResultAndModel<R, CP> setStatus(ResultStatus status) {
        this.status = status;
        return this;
    }

    public CP getServices() {
        return services;
    }

    public ResultAndModel<R, CP> setServices(CP services) {
        this.services = services;
        return this;
    }

    public Map<String,Object> asUnmodifiableModel() {
        return Collections.unmodifiableMap(model);
    }

    @Override
    public String toString() {
        return "ResultAndModel{" +
                "result=" + result +
                ", model=" + model +
                ", status=" + status +
                '}';
    }
}
