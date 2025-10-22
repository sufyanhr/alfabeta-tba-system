/*
MIT License

Copyright (c) 2016-2023, Openkoda CDX Sp. z o.o. Sp. K. <openkoda.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice
shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.core.form;

import com.openkoda.core.helper.PrivilegeHelper;
import com.openkoda.core.security.OrganizationUser;
import com.openkoda.model.PrivilegeBase;
import com.openkoda.model.common.LongIdEntity;
import com.openkoda.uicomponent.annotation.Autocomplete;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.openkoda.core.form.FrontendMappingFieldDefinition.*;
import static com.openkoda.core.helper.PrivilegeHelper.valueOfString;

public class FormFieldDefinitionBuilder<V> extends FormFieldDefinitionBuilderStart {


    @Autocomplete
    public FrontendMappingFieldDefinition[] getFieldsAsArray() {
        return fields.toArray(new FrontendMappingFieldDefinition[fields.size()]);
    }
    @Autocomplete
    public Tuple2<FrontendMappingFieldDefinition, Function<?, String>>[] getFieldValidatorsAsArray() {
        return fieldValidators.toArray(new Tuple2[fieldValidators.size()]);
    }
    @Autocomplete
    public Function<? extends Form, Map<String, String>>[]  getFormValidatorsAsArray() {
        return formValidators.toArray(new Function[formValidators.size()]);
    }
    @Autocomplete
    public FormFieldDefinitionBuilder(String formName, PrivilegeBase defaultReadPrivilege, PrivilegeBase defaultWritePrivilege) {
        super(formName, defaultReadPrivilege, defaultWritePrivilege);
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> additionalCss(String additionalCss) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, additionalCss));
        return this;
    }

    @Autocomplete
    public FormFieldDefinitionBuilder<V> searchable() {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinitionWithSearchEnabled(formName, lastField));
        return this;
    }

    @Autocomplete
    public FormFieldDefinitionBuilder<V> sqlFormula(String sqlFormula) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinitionWithSqlFormula(formName, lastField, sqlFormula));
        return this;
    }

    @Autocomplete
    public FormFieldDefinitionBuilder<V> additionalPrivileges(PrivilegeBase readPrivilege, PrivilegeBase writePrivilege) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, readPrivilege, writePrivilege));
        return this;
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> additionalPrivileges(String readPrivilege, String writePrivilege) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, (PrivilegeBase) valueOfString(readPrivilege), (PrivilegeBase) valueOfString(writePrivilege)));
        return this;
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> additionalPrivileges(BiFunction<OrganizationUser, LongIdEntity, Boolean> canReadCheck, BiFunction<OrganizationUser, LongIdEntity, Boolean> canWriteCheck) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, canReadCheck, canWriteCheck));
        return this;
    }

    @Autocomplete
    public FormFieldDefinitionBuilder<V> visible(BiFunction<OrganizationUser, LongIdEntity, Boolean> canReadCheck) {
        boolean strictWrite = lastField.isStrictWriteAccess();
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, canReadCheck, lastField.canWriteCheck));
        lastField.setStrictReadAccess(true);
        lastField.setStrictWriteAccess(strictWrite);
        return this;
    }
    
    @Autocomplete
    public FormFieldDefinitionBuilder<V> enabled(BiFunction<OrganizationUser, LongIdEntity, Boolean> canWriteCheck) {
        boolean strictRead = lastField.isStrictReadAccess();
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, lastField.canReadCheck, canWriteCheck));
        lastField.setStrictWriteAccess(true);
        lastField.setStrictReadAccess(strictRead);
        return this;
    }
    
    @Autocomplete
    public FormFieldDefinitionBuilder<V> additionalAction(String actionLabelKey, String actionUrl, PrivilegeBase additionalActionPrivilege) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, actionLabelKey, actionUrl, additionalActionPrivilege));
        return this;
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> additionalAction(String actionLabelKey, String actionUrl, String privilegeNameAsString) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, actionLabelKey, actionUrl, (PrivilegeBase) PrivilegeHelper.valueOfString(privilegeNameAsString)));
        return this;
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> valueSupplier(Function<AbstractForm, Object> valueSupplier) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, valueSupplier));
        return this;
    }
    @Autocomplete
    public <V2> FormFieldDefinitionBuilder<V2> valueConverters(Function<V, V2> toEntityValue, Function<Object, Object> toDtoValue) {
        fields.set(fields.size() - 1, lastField = createFormFieldDefinition(formName, lastField, toEntityValue, toDtoValue));
        return (FormFieldDefinitionBuilder<V2>) this;
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> referenceDescriptionSource(String source) {
        FrontendMappingFieldDefinition datalistField = fields.get(fields.size() - 2);
        FrontendMappingFieldDefinition dropdownField = fields.get(fields.size() - 1);
        fields.set(fields.size() - 2, createFormFieldDefinition(formName, datalistField, (f, d) -> d.dictionary(dropdownField.referencedEntityKey, source)));
        return this;
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> validate(Function<V, String> validatorReturningErrorCode) {
        fieldValidators.add(Tuples.of(lastField, validatorReturningErrorCode));
        return this;
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> withPreselectedValue(String preselectedValue) {
        lastField.preselectedValue = preselectedValue;
        return this;
    }
    @Autocomplete
    public FormFieldDefinitionBuilder<V> validateForm(Function<? extends Form, Map<String, String>> validatorReturningRejectedFieldToErrorCodeMap) {
        formValidators.add(validatorReturningRejectedFieldToErrorCodeMap);
        return this;
    }
    @Autocomplete
    public <VT> FormFieldDefinitionBuilder<VT> valueType(Class<VT> c) {
        return (FormFieldDefinitionBuilder<VT>)this;
    }



}