package com.alfabeta.core.flow.mbean;

import com.alfabeta.core.flow.PageModelMap;

/**
 * Functional interface for mapping PageModelMap to another PageModelMap or view logic.
 */
@FunctionalInterface
public interface PageModelFunction {
    PageModelMap apply(PageModelMap model);
}
