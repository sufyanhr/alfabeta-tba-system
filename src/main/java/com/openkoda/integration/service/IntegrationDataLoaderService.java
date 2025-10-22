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

package com.openkoda.integration.service;

import com.openkoda.core.customisation.CoreSettledEvent;
import com.openkoda.core.helper.SpringProfilesHelper;
import com.openkoda.integration.controller.IntegrationComponentProvider;
import com.openkoda.integration.model.IntegrationPrivilege;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

import static com.openkoda.core.lifecycle.BaseDatabaseInitializer.ROLE_ADMIN;

/**
 * @author Martyna Litkowska (mlitkowska@stratoflow.com)
 * @since 2019-10-11
 */
@Service
public class IntegrationDataLoaderService extends IntegrationComponentProvider {

    @EventListener(CoreSettledEvent.class)
    public void updateOrgAdminAndUserRole() {
        if (SpringProfilesHelper.isInitializationProfile()) {
            services.module.addModulePrivilegesToRole(ROLE_ADMIN, new HashSet<>(Arrays.asList(IntegrationPrivilege.values())));
        }
    }
}
