
package com.alfabeta.controller.api;

import com.alfabeta.controller.ComponentProvider;
import com.alfabeta.controller.DefaultComponentProvider;
import com.alfabeta.controller.common.URLConstants;
import com.alfabeta.core.flow.Flow;
import com.alfabeta.core.flow.PageAttr;
import com.alfabeta.core.form.CRUDControllerConfiguration;
import com.alfabeta.core.form.ReflectionBasedEntityForm;
import com.alfabeta.core.security.HasSecurityRules;
import com.alfabeta.model.PrivilegeBase;
import com.alfabeta.model.common.SearchableOrganizationRelatedEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.HashMap;


abstract public class CRUDApiController<E extends SearchableOrganizationRelatedEntity> extends ComponentProvider implements URLConstants, HasSecurityRules {

    @Inject
    protected DefaultComponentProvider componentProvider;

    private final String key;

    public CRUDApiController(String key) {
        this.key = key;
    }

    @GetMapping(value=_ALL, produces=MediaType.APPLICATION_JSON_VALUE)
    public Object getAll(
            @PathVariable(name=ORGANIZATIONID, required = false) Long organizationId,
            @Qualifier("obj") Pageable aPageable,
            @RequestParam(required = false, defaultValue = "", name = "obj_search") String search) {
        debug("[getAll]");
        CRUDControllerConfiguration conf = controllers.apiCrudControllerConfigurationMap.get(key);
        PrivilegeBase privilege = conf.getGetAllPrivilege();
        if (not(hasGlobalOrOrgPrivilege(privilege, organizationId))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return Flow.init(componentProvider)
                .then( a -> (Page<SearchableOrganizationRelatedEntity>) conf.getSecureRepository().search(search, organizationId, conf.getAdditionalSpecification(), aPageable))
                .thenSet(genericTableViewMap, a -> ReflectionBasedEntityForm.calculateFieldsValuesWithReadPrivilegesAsMap(conf.getFrontendMappingDefinition(), a.result, conf.getTableFormFieldNames()))
                .execute()
                .getAsMap(genericTableViewMap);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object settings(
            @PathVariable(name = ID) Long objectId,
            @PathVariable(name = ORGANIZATIONID, required = false) Long organizationId
            ) {

        CRUDControllerConfiguration conf = controllers.apiCrudControllerConfigurationMap.get(key);
        PrivilegeBase privilege = conf.getGetSettingsPrivilege();
        if (not(hasGlobalOrOrgPrivilege(privilege, organizationId))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return Flow.init(componentProvider, objectId)
                .then(a -> conf.getSecureRepository().findOne(objectId))
                .thenSet(conf.getEntityPageAttribute(), a -> ReflectionBasedEntityForm.calculateFieldValuesWithReadPrivilegesAsMap(conf.getFrontendMappingDefinition(), (SearchableOrganizationRelatedEntity) a.result, conf.getFrontendMappingDefinition().getNamesOfValuedTypeFields()))
                .execute().getAsMap(conf.getEntityPageAttribute());
    }

    @PostMapping(value="{id}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object save(
            @PathVariable(ID) Long objectId,
            @PathVariable(name = ORGANIZATIONID, required = false) Long organizationId,
            @RequestBody HashMap<String,String> params) {
        debug("[saveNew]");
        CRUDControllerConfiguration conf = controllers.apiCrudControllerConfigurationMap.get(key);
        PrivilegeBase privilege = conf.getPostSavePrivilege();
        if (not(hasGlobalOrOrgPrivilege(privilege, organizationId))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return
                Flow.init(componentProvider)
                .thenSet((PageAttr<E>) conf.getEntityPageAttribute(), a -> (E) conf.getSecureRepository().findOne(objectId))
                .thenSet((PageAttr<ReflectionBasedEntityForm>) conf.getFormAttribute(), a -> (ReflectionBasedEntityForm) conf.createNewForm(organizationId, a.result))
                 .then(a -> a.result.prepareDto(params, (E) a.model.get(conf.getEntityPageAttribute())))
                 .thenSet(isValid,a -> services.validation.validateAndPopulateToEntity((ReflectionBasedEntityForm) a.model.get(conf.getFormAttribute()), (E) a.model.get(conf.getEntityPageAttribute())))
                 .thenSet(longEntityId, a -> {
                     if(a.result) {
                         return ((E) conf.getSecureRepository().saveOne(a.model.get(conf.getEntityPageAttribute()))).getId();
                     }
                     return null;
                 })
                .execute()
                .getAsMap(isValid, longEntityId);
    }

    @PostMapping(value="create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveNew(
            @PathVariable(name = ORGANIZATIONID, required = false) Long organizationId,
            @RequestBody HashMap<String,String> params) {
        debug("[saveNew]");
        CRUDControllerConfiguration conf = controllers.apiCrudControllerConfigurationMap.get(key);
        PrivilegeBase privilege = conf.getPostNewPrivilege();
        if (not(hasGlobalOrOrgPrivilege(privilege, organizationId))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return Flow.init(componentProvider)
                .thenSet((PageAttr<E>) conf.getEntityPageAttribute(), a -> (E) conf.createNewEntity(organizationId))
                .thenSet((PageAttr<ReflectionBasedEntityForm>) conf.getFormAttribute(), a -> (ReflectionBasedEntityForm) conf.createNewForm(organizationId, a.result))
                .then(a -> a.result.prepareDto(params, (SearchableOrganizationRelatedEntity) a.model.get(conf.getEntityPageAttribute())))
                .thenSet(isValid,a -> services.validation.validateAndPopulateToEntity((ReflectionBasedEntityForm) a.model.get(conf.getFormAttribute()), (E) a.model.get(conf.getEntityPageAttribute())))
                .thenSet(longEntityId, a -> {
                    if(a.result) {
                        return ((E) conf.getSecureRepository().saveOne(a.model.get(conf.getEntityPageAttribute()))).getId();
                    }
                    return null;
                })
                .execute()
                .getAsMap(isValid, longEntityId);
    }

    @PostMapping(value="{id}/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object remove(
            @PathVariable(name=ID) Long objectId,
            @PathVariable(name = ORGANIZATIONID, required = false) Long organizationId) {
        CRUDControllerConfiguration conf = controllers.apiCrudControllerConfigurationMap.get(key);
        PrivilegeBase privilege = conf.getPostRemovePrivilege();
        if (not(hasGlobalOrOrgPrivilege(privilege, organizationId))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return Flow.init(componentProvider, objectId)
                .then(a -> conf.getSecureRepository().deleteOne(objectId))
                .execute();
    }
}
