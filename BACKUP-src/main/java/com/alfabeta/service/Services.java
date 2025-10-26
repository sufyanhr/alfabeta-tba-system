/*
MIT License

Copyright (c) 2016-2023, Openkoda CDX Sp. z o.o. Sp. K. <alfabeta.com>

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

package com.alfabeta.service;

import com.alfabeta.core.customisation.CustomisationService;
import com.alfabeta.core.customisation.ServerJSRunner;
import com.alfabeta.core.flow.TransactionalExecutor;
import com.alfabeta.core.helper.UrlHelper;
import com.alfabeta.core.multitenancy.TenantResolver;
import com.alfabeta.core.security.RunAsService;
import com.alfabeta.core.service.*;
import com.alfabeta.core.service.email.EmailConstructor;
import com.alfabeta.core.service.email.EmailSender;
import com.alfabeta.core.service.email.EmailService;
import com.alfabeta.core.service.event.ApplicationEventService;
import com.alfabeta.core.service.event.ClusterEventSenderService;
import com.alfabeta.core.service.event.EventListenerService;
import com.alfabeta.core.service.event.SchedulerService;
import com.alfabeta.core.service.form.FormService;
import com.alfabeta.core.service.module.ModuleService;
import com.alfabeta.core.service.pdf.PdfConstructor;
import com.alfabeta.core.service.system.DatabaseValidationService;
import com.alfabeta.core.service.system.SystemHealthStatusService;
import com.alfabeta.service.captcha.CaptchaService;
import com.alfabeta.service.csv.CsvService;
import com.alfabeta.service.dynamicentity.DynamicEntityRegistrationService;
import com.alfabeta.service.dynamicentity.DynamicEntityService;
import com.alfabeta.service.export.ComponentExportService;
import com.alfabeta.service.export.ZipComponentImportService;
import com.alfabeta.service.notification.NotificationService;
import com.alfabeta.service.openai.ChatGPTService;
import com.alfabeta.service.organization.OrganizationService;
import com.alfabeta.service.user.*;
import com.alfabeta.uicomponent.JsParser;
import com.alfabeta.uicomponent.UtilServices;
import com.alfabeta.uicomponent.live.LiveDataServices;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author Arkadiusz Drysch (adrysch@stratoflow.com)
 * 
 */
@Component("allServices")
public class Services {
    @Inject public ApplicationEventService applicationEvent;
    @Inject public RoleService role;
    @Inject public BasicPrivilegeService privilege;
    @Inject public OrganizationService organization;
    @Inject public TransactionalExecutor transactionalExecutor;
    @Inject public EmailSender email;
    @Inject public EmailConstructor emailConstructor;
    @Inject public ModuleService module;
    @Inject public UserService user;
    @Inject public ValidationService validation;
    @Inject public FrontendResourceService frontendResource;
    @Inject public ServerJSRunner serverJSRunner;
    @Inject public UrlHelper url;
    @Inject public TokenService token;
    @Inject public ZipService zipService;
    @Inject public RunAsService runAs;
    @Inject public EventListenerService eventListener;
    @Inject public SessionService sessionService;
    @Inject public SchedulerService scheduler;
    @Inject public EmailService emailService;
    @Inject public LogConfigService logConfig;
    @Inject public PdfConstructor pdfConstructor;
    @Inject public NotificationService notification;
    @Inject public SystemHealthStatusService systemStatus;
    @Inject public DatabaseValidationService databaseValidationService;
    @Inject public ApiKeyService apiKey;
    @Inject public ClusterEventSenderService clusterEventSender;
    @Inject public ThymeleafService thymeleaf;
    @Inject public FileService file;
    @Inject public CaptchaService captcha;
    @Inject public RestClientService restClient;
    @Inject public TenantResolver tenantResolver;
    @Inject public FrontendMappingDefinitionService frontendMappingDefinition;
    @Inject public WebsocketService websocket;
    @Inject public UserRoleService userRole;
    @Inject public LiveDataServices data;
    @Inject public UtilServices util;
    @Inject public CustomisationService customisation;
    @Inject public FormService form;
    @Inject public ComponentExportService componentExport;
    @Inject public ZipComponentImportService zipComponentImport;
    @Inject public DatabaseValidationService databaseValidation;
    @Inject public DynamicEntityRegistrationService dynamicEntityRegistration;
    @Inject public JsParser jsParser;
    @Inject public DynamicEntityService dynamicEntity;
    @Inject public CsvService csv;
    @Inject public ChatGPTService chatGPTService;
}
