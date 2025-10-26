package com.alfabeta.core.mbean;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import java.util.List;

/**
 * Defines standard page attributes available across all views and flows.
 * This acts as a contract (interface), not a data container.
 */
public interface BasePageAttributes {
    PageAttr<Boolean> IS_ERROR = new PageAttr<>("isError");
    PageAttr<String> MESSAGE = new PageAttr<>("message");
    PageAttr<String> ERROR = new PageAttr<>("error");
    PageAttr<Exception> EXCEPTION = new PageAttr<>("exception");
    PageAttr<BindingResult> BINDING_RESULT = new PageAttr<>("bindingResult");
    PageAttr<List<ObjectError>> BINDING_RESULT_ALL_ERRORS = new PageAttr<>("bindingResultAllErrors");
}
