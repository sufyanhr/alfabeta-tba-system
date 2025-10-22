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

package com.openkoda.controller;

import com.openkoda.core.customisation.CustomisationService;
import com.openkoda.core.form.ReflectionBasedEntityForm;
import com.openkoda.form.PageBuilderForm;
import com.openkoda.model.Privilege;
import com.openkoda.model.component.FrontendResource;
import com.openkoda.repository.SecureFormRepository;
import com.openkoda.repository.SecureFrontendResourceRepository;
import com.openkoda.repository.SecureServerJsRepository;
import com.openkoda.repository.ai.SecureQueryReportRepository;
import com.openkoda.repository.organization.SecureOrganizationRepository;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.openkoda.form.FrontendMappingDefinitions.*;
import static com.openkoda.model.Privilege.canCreateReports;
import static com.openkoda.model.Privilege.canReadReports;

/**
 * Used for registration of generic controllers configurations {@link com.openkoda.core.form.CRUDControllerConfiguration} on application start.
 * It is a place where generic controllers are defined.
 * {@link CRUDControllerHtml} provides request handlers for typical operations which are available out of the box after controller registration
 */
@Component
public class CRUDControllers {

    @Inject
    CustomisationService customisationService;
    @Inject
    HtmlCRUDControllerConfigurationMap htmlCrudControllerConfigurationMap;
    @Inject
    SecureServerJsRepository serverJsRepository;
    @Inject
    SecureFrontendResourceRepository frontendResourceRepository;
    @Inject
    SecureOrganizationRepository secureOrganizationRepository;
    @Inject
    SecureFormRepository formRepository;
    @Inject
    SecureQueryReportRepository queryReportRepository;

    /**
     * Registers generic controllers using {@link CustomisationService#registerOnApplicationStartListener(Consumer)}
     */
    @PostConstruct
    void init() {

        customisationService.registerOnApplicationStartListener(
                a -> htmlCrudControllerConfigurationMap.registerCRUDController(
                                organizationsApi, secureOrganizationRepository, ReflectionBasedEntityForm.class, Privilege.readOrgData,Privilege.manageOrgData)
                        .setGenericTableFields("id","name"));
        customisationService.registerOnApplicationStartListener(
                a -> htmlCrudControllerConfigurationMap.registerCRUDController(PAGE_BUILDER_FORM,
                                PageBuilderForm.pageBuilderForm, frontendResourceRepository, PageBuilderForm.class, Privilege.canAccessGlobalSettings,Privilege.canAccessGlobalSettings)
                        .setGenericTableFields("name")
                        .setNavigationFragment("navigation-fragments::configuration-nav-tabs('builder')")
                        .setMenuItem("configuration")
                        .setAdditionalPredicate((r, q, cb) -> cb.equal(r.get("resourceType"), FrontendResource.ResourceType.DASHBOARD)));
        customisationService.registerOnApplicationStartListener(
                a -> htmlCrudControllerConfigurationMap.registerCRUDController(
                                frontendResourceForm, frontendResourceRepository, ReflectionBasedEntityForm.class)
                        .setGenericTableFields("name","includeInSitemap","type")
                        .setNavigationFragment("navigation-fragments::configuration-nav-tabs('resources')")
                        .setMenuItem("resources")
                        .setTableView("frontend-resource-all")
                        .setAdditionalPredicate((root, query, cb) -> cb.and(cb.equal(root.get("resourceType"), FrontendResource.ResourceType.RESOURCE))));
        customisationService.registerOnApplicationStartListener(
                a -> htmlCrudControllerConfigurationMap.registerCRUDController(
                                queryReportForm, queryReportRepository, ReflectionBasedEntityForm.class,  canReadReports, canCreateReports)
                        .setGenericTableFields("name","organizationId")
                        .setTableView("frontend-resource/global/report-all")
                        .setSettingsView("report-data-table")
                        .setNavigationFragment("navigation-fragments::reporting-nav-tabs('reports')")
                        .setMenuItem("reports"));

    }

}
