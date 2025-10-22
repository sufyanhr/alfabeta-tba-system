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

package com.openkoda.core.configuration;

import com.openkoda.controller.common.URLConstants;
import com.openkoda.core.configuration.TemplatePathFilteringProcessor.FilteredTemplatePath;
import com.openkoda.core.multitenancy.QueryExecutor;
import com.openkoda.core.multitenancy.TenantResolver;
import com.openkoda.core.service.FrontendResourceService;
import com.openkoda.core.tracker.LoggingComponentWithRequestId;
import com.openkoda.model.component.FrontendResource;
import com.openkoda.model.component.FrontendResource.Type;
import com.openkoda.service.export.ClasspathComponentImportService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;

import java.util.List;
import java.util.Map;

import static com.openkoda.core.service.FrontendResourceService.frontendResourceFolderClasspath;
import static com.openkoda.core.service.FrontendResourceService.frontendResourceTemplateNamePrefix;
import static com.openkoda.service.export.FolderPathConstants.FRONTEND_RESOURCE_;
import static com.openkoda.service.export.FolderPathConstants.UI_COMPONENT_;

/**
 * <p>Extends TemplateResolver routine according to following rules:</p>
 * <p>If template name starts with 'frontend-resource#' then it tries to load the template from the database, otherwise
 * uses the standard routine of loading the template from classpath.</p>
 * <p>If the template is not in the database, then it attempts to create it from a file located in classpath folder.</p>
 * <p>Also supports a few special parameters for testing or reading the template always from resources. </p>
 */
public class FrontendResourceOrClassLoaderTemplateResolver extends ClassLoaderTemplateResolver implements LoggingComponentWithRequestId {

    /**
     * Always read frontend resource from filesystem (classpath resource)
     */
    private final boolean frontendResourceLoadAlwaysFromResources;
    /**
     * Create frontend resource stub if not exist
     */
    private final boolean frontendResourceCreateIfNotExist;

    @Autowired
    private HttpServletRequest request;

    /**
     * Frontend resource service
     */
    FrontendResourceService frontendResourceService;
    ClasspathComponentImportService classpathComponentImportService;

    /**
     * Query executor for database operations
     */
    QueryExecutor queryExecutor;

    private final TemplatePathFilteringProcessor pathFilteringProcessor;


    public FrontendResourceOrClassLoaderTemplateResolver(QueryExecutor queryExecutor,
                                                         FrontendResourceService frontendResourceService,
                                                         ClasspathComponentImportService classpathComponentImportService,
                                                         boolean frontendResourceLoadAlwaysFromResources,
                                                         boolean frontendResourceCreateIfNotExist,
                                                         TemplatePathFilteringProcessor pathFilteringProcessor) {
        this.queryExecutor = queryExecutor;
        this.frontendResourceService = frontendResourceService;
        this.classpathComponentImportService = classpathComponentImportService;
        this.frontendResourceLoadAlwaysFromResources = frontendResourceLoadAlwaysFromResources;
        this.frontendResourceCreateIfNotExist = frontendResourceCreateIfNotExist;
        this.pathFilteringProcessor = pathFilteringProcessor;
    }

    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        trace("[computeTemplateResource] ownerTemplate {} template {} resource {}", ownerTemplate, template, resourceName);

        //if the template name starts with the frontendResourceTemplateNamePrefix, use the custom routine
        if (StringUtils.startsWith(template, frontendResourceTemplateNamePrefix)) {
            TenantResolver.TenantedResource tenantedResource = TenantResolver.getTenantedResource();

            //resolve the actual template name
            FilteredTemplatePath filteredTemplatePath = pathFilteringProcessor.processTemplatePath(template,
                    resourceName, tenantedResource.accessLevel);

            //if request has URLConstants#RESOURCE parameter, then return template content from filesystem, not from the DB
            boolean isResourceTesting = (isHttpRequest() && request.getParameter(URLConstants.RESOURCE) != null);
            if(isResourceTesting) {
                return new StringTemplateResource(
                        getResourceContent(filteredTemplatePath.getFrontendResourceEntryName(),
                                filteredTemplatePath.getAccessLevel(), tenantedResource.organizationId));
            }


            //...else, try to find template in database
            FrontendResource.AccessLevel finalAccessLevel = filteredTemplatePath.getAccessLevel();
            List<Object[]> entries = queryExecutor.runEntityManagerOperationInTransaction(em ->
                    em.createQuery("select c, case  " +
                                            "when c.accessLevel = 'PUBLIC' and c.organizationId is not null then 1 " +
                                            "when c.accessLevel = 'PUBLIC' and c.organizationId is null then 2 " +
                                            "when c.accessLevel = 'GLOBAL' and c.organizationId is not null then 1 " +
                                            "when c.accessLevel = 'GLOBAL' and c.organizationId is null then 2 " +
                                            "when c.accessLevel = 'ORGANIZATION' and c.organizationId is not null then 1 " +
                                            "when c.accessLevel = 'ORGANIZATION' and c.organizationId is null then 2 " +
                                            "when c.accessLevel = 'GLOBAL' and 'ORGANIZATION' = :p3 and c.organizationId is not null then 3 " +
                                            "when c.accessLevel = 'GLOBAL' and 'ORGANIZATION' = :p3 and c.organizationId is null then 4 " +
                                            "else 10 end as priority " +
                                            "from FrontendResource c " +
                                            "where " +
                                            "c.name = :p1 and " +
                                            "(cast (c.accessLevel as text) = :p3 or (cast (c.accessLevel as text) = 'GLOBAL' and 'ORGANIZATION' = :p3)) and " +
                                            "(c.organizationId = :p2 OR c.organizationId is NULL) order by priority limit 1"
                                    , Object[].class)
                            .setParameter("p1", filteredTemplatePath.getFrontendResourceEntryName())
                            .setParameter("p2", tenantedResource.organizationId)
                            .setParameter("p3", finalAccessLevel.toString())
                            .getResultList());
            FrontendResource entry = entries == null || entries.isEmpty() ? null : (FrontendResource) entries.get(0)[0];

            //if entry not found in the database...
            if (entry == null) {

                entry = (FrontendResource) classpathComponentImportService.loadResourceFromFile(FRONTEND_RESOURCE_,
                        filteredTemplatePath.getAccessLevel(), tenantedResource.organizationId,
                        filteredTemplatePath.getFrontendResourceEntryName());

                if (entry == null) {
//                    try ui component
                    entry = (FrontendResource) classpathComponentImportService.loadResourceFromFile(UI_COMPONENT_,
                            filteredTemplatePath.getAccessLevel(), tenantedResource.organizationId,
                            filteredTemplatePath.getFrontendResourceEntryName());
                    if(entry == null && filteredTemplatePath.getAccessLevel().equals(FrontendResource.AccessLevel.ORGANIZATION)) {
                        // try to find global level ui component
                        entry = (FrontendResource) classpathComponentImportService.loadResourceFromFile(UI_COMPONENT_,
                                FrontendResource.AccessLevel.GLOBAL, tenantedResource.organizationId,
                                filteredTemplatePath.getFrontendResourceEntryName());
                    }
                }

                if (entry == null) {
                    //...try to create if from filesystem
                    entry = createEntry(filteredTemplatePath.getFrontendResourceEntryName(),
                            filteredTemplatePath.getAccessLevel(), tenantedResource.organizationId);
                    if(entry == null && filteredTemplatePath.getAccessLevel().equals(FrontendResource.AccessLevel.ORGANIZATION)) {
                        // try to find global level ui component
                        entry =createEntry(filteredTemplatePath.getFrontendResourceEntryName(),
                                FrontendResource.AccessLevel.GLOBAL, tenantedResource.organizationId);
                    }
                }

                //if the entry was not created (eg. not found in filesystem), return error template
                if (entry == null) {
                    return getErrorTemplate(configuration, ownerTemplate, template, characterEncoding, templateResolutionAttributes);
                }
            } else if (frontendResourceLoadAlwaysFromResources) {
                // else set content from filesystem if the resource should always be read from filesystem
                entry.setContent(
                        getContentOrNull(entry.getType(), filteredTemplatePath.getFrontendResourceEntryName(),
                                filteredTemplatePath.getAccessLevel(), tenantedResource.organizationId));
            } else if (not(entry.isContentExists())) {
                // else if the database entry does not have content, read it from filesystem
                entry = fillFrontendResourceEntryContentFromResource(entry,
                        filteredTemplatePath.getFrontendResourceEntryName(), filteredTemplatePath.getAccessLevel(),
                        tenantedResource.organizationId);
            }

            // if request parameter indicates it's a test of draft version of the Frontend resource,
            // then return draft content, otherwise return regular content
            boolean isDraftTesting = (isHttpRequest() && request.getParameter(URLConstants.DRAFT) != null);
            String content = entry.isDraft() && isDraftTesting ? entry.getDraftContent() : entry.getContent();
            return new StringTemplateResource(content);
        }
        //...else delegate template resolution to the super class implementation.
        return super.computeTemplateResource(configuration, ownerTemplate,
                template, resourceName, characterEncoding, templateResolutionAttributes);
    }



    private ITemplateResource getErrorTemplate(IEngineConfiguration configuration, String ownerTemplate, String template, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        warn("[computeTemplateResource] template '{}' not found neither in db nor in resources", template);
        return super.computeTemplateResource(configuration, ownerTemplate,
                frontendResourceTemplateNamePrefix + "error", frontendResourceFolderClasspath + "error.html", characterEncoding, templateResolutionAttributes);
    }


    private FrontendResource createEntry(String entryName, FrontendResource.AccessLevel accessLevel, Long organizationId) {
        debug("[createEntry] {}", entryName);

        FrontendResource.Type type = Type.getEntryTypeFromPath(entryName);
        String content = getContentOrNull(type, entryName, accessLevel, organizationId);

        //create frontendResource entry when content in resources exists
        //or stub content when not in resources
        if (content != null || frontendResourceCreateIfNotExist) {
            FrontendResource result = new FrontendResource();
            result.setName(entryName);
            result.setType(type);
            result.setContent(content);
            result.setAccessLevel(accessLevel);
            result.setOrganizationId(organizationId);
            result.setEmbeddable(false);
            result.setIncludeInSitemap(false);
            queryExecutor.runEntityManagerOperationInTransaction(em -> {em.persist(result); em.flush(); return null;});
            return result;
        }

        return null;
    }

    private FrontendResource fillFrontendResourceEntryContentFromResource(final FrontendResource entry, String entryName, FrontendResource.AccessLevel accessLevel, Long organizationId) {
        return queryExecutor.runEntityManagerOperationInTransaction(em -> {
            entry.setContent(getContentOrDefault(entry.getType(), entryName, accessLevel, organizationId));
            return em.merge(entry);
        });
    }

    private String getContentOrNull(Type type, String frontendResourceEntryName, FrontendResource.AccessLevel accessLevel, Long organizationId) {
        debug("[getContentOrNull] {}", frontendResourceEntryName);

        String content = frontendResourceService.getContentOrDefault(type, frontendResourceEntryName, accessLevel, organizationId);
        if(StringUtils.isNotBlank(content)) {
            return content;
        }

        return null;
    }

    private String getContentOrDefault(Type result, String frontendResourceEntryName, FrontendResource.AccessLevel accessLevel, Long organizationId) {
        debug("[getContentOrDefault] {}", frontendResourceEntryName);
        String content = getContentOrNull(result, frontendResourceEntryName, accessLevel, organizationId);
        return content != null ? content : String.format("Add content here [%s]", frontendResourceTemplateNamePrefix + frontendResourceEntryName);
    }

    private String getResourceContent(String frontendResourceEntryName, FrontendResource.AccessLevel accessLevel, Long organizationId) {
        FrontendResource.Type type = Type.getEntryTypeFromPath(frontendResourceEntryName);
        return frontendResourceService.getContentOrDefault(type, frontendResourceEntryName, accessLevel, organizationId);
    }

    private boolean isHttpRequest() {
        return request != null && RequestContextHolder.getRequestAttributes() != null;
    }
}
