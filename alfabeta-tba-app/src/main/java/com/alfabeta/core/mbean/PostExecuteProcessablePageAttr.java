package com.alfabeta.core.mbean;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * A page attribute that allows for post-processing logic after controller execution.
 * Commonly used to manipulate model data after main flow execution.
 *
 * @param <T> type of the attribute value
 */
public class PostExecuteProcessablePageAttr<T> extends PageAttr<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Consumer<T> postProcessor;

    public PostExecuteProcessablePageAttr(String key, Consumer<T> postProcessor) {
        super(key);
        this.postProcessor = postProcessor;
    }

    public Consumer<T> getPostProcessor() {
        return postProcessor;
    }

    public void process(T value) {
        if (postProcessor != null && value != null) {
            postProcessor.accept(value);
        }
    }
}
