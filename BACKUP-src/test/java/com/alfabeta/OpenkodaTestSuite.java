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

package com.alfabeta;

import com.alfabeta.core.form.ParamNameDataBinderTest;
import com.alfabeta.core.helper.RuleSpelHelperTests;
import com.alfabeta.core.helper.UrlHelperTest;
import com.alfabeta.core.service.LogConfigServiceTest;
import com.alfabeta.core.service.backup.BackupWriterTest;
import com.alfabeta.service.*;
import com.alfabeta.service.map.MapServiceTest;
import com.alfabeta.uicomponent.JsFlowRunnerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Martyna Litkowska (mlitkowska@stratoflow.com)
 * @since 2019-01-31
 */
@Suite
@SelectClasses({
        MapServiceTest.class,
        BackupServiceTest.class,
        EventListenerServiceTest.class,
        FrontendResourceServiceTest.class,
        PushNotificationServiceTest.class,
        RoleServiceTest.class,
        SchedulerServiceTests.class,
        UserServiceTest.class,
        JsFlowRunnerTest.class,
        ParamNameDataBinderTest.class,
        RuleSpelHelperTests.class,
        UrlHelperTest.class,
        BackupWriterTest.class,
        LogConfigServiceTest.class
})
public class OpenkodaTestSuite {
}
