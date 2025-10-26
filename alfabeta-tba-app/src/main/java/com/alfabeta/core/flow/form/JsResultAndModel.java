

package com.alfabeta.core.flow.form;

import com.alfabeta.core.flow.Flow;
import com.alfabeta.core.flow.PageModelMap;
import com.alfabeta.core.flow.ResultAndModel;
import com.alfabeta.core.form.AbstractOrganizationRelatedEntityForm;

import java.util.Map;

public class JsResultAndModel<R, CP, F extends AbstractOrganizationRelatedEntityForm> extends ResultAndModel<R, CP> {

    public final F form;

    protected JsResultAndModel(PageModelMap model, R result, CP services, Map<String, Object> params, F form) {
        super(model, result, services, params);
        this.form = form;
    }

    //TODO: class to one package up, make this class package
    public static <CP, FP extends AbstractOrganizationRelatedEntityForm> JsResultAndModel constructNew(CP services, Map params, FP form) {
        JsResultAndModel result = new JsResultAndModel(new PageModelMap(), null, services, Flow.initParamsMap(params), form);
        return result;

    }


}
