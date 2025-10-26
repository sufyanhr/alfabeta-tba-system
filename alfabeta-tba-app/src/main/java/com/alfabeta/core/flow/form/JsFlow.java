
package com.alfabeta.core.flow.form;

import com.alfabeta.core.flow.Flow;
import com.alfabeta.core.flow.PageModelMap;
import com.alfabeta.core.flow.ResultAndModel;
import com.alfabeta.core.flow.TransactionalExecutor;
import com.alfabeta.core.form.AbstractOrganizationRelatedEntityForm;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JsFlow<I, O, CP> extends Flow<I, O, CP> {

    private final AbstractOrganizationRelatedEntityForm form;

    protected JsFlow(Map<String, Object> params, CP services, Function<ResultAndModel<I, CP>, O> f, Supplier<TransactionalExecutor> transactionalExecutorProvider, Consumer<Function> onThen, AbstractOrganizationRelatedEntityForm form) {
        super(params, services, f, transactionalExecutorProvider, onThen);
        this.form = form;
    }

    @Override
    protected <II, IO, ICP> Flow<II, IO, ICP> constructFlow(Map<String, Object> params, ICP services, Function<ResultAndModel<II, ICP>, IO> f, Supplier<TransactionalExecutor> transactionalExecutorProvider, Consumer<Function> onThen) {
        return new JsFlow(params, services, f, transactionalExecutorProvider, onThen, form);
    }

    @Override
    protected <IR, ICP> ResultAndModel<IR, ICP> constructResultAndModel(PageModelMap model, IR result, ICP services, Map<String, Object> params) {
        return new JsResultAndModel<>(model, result, services, params, form);
    }

    public static <A, CP> Flow<A, A, CP> init(CP services, Map params, AbstractOrganizationRelatedEntityForm form) {
        return new JsFlow<>(initParamsMap(params), services, a->a.result, null, null, form);
    }

}
