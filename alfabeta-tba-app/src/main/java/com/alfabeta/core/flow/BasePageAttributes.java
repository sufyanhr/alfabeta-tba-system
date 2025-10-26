

package com.alfabeta.core.flow;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author Arkadiusz Drysch (adrysch@stratoflow.com)
 * @since 28.01.17
 */
public interface BasePageAttributes {
    PageAttr<Boolean> isError = new PageAttr<>("isError");
    PageAttr<String> message = new PageAttr<>("message");
    PageAttr<String> error = new PageAttr<>("error");
    PageAttr<Exception> exception = new PageAttr<>("exception");
    PageAttr<BindingResult> bindingResult = new PageAttr<>("bindingResult");
    PageAttr<List<ObjectError>> bindingResultAllErrors = new PageAttr<>("bindingResultAllErrors");
}
