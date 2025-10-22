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

package com.openkoda.dto.system;

import com.openkoda.dto.CanonicalObject;
import com.openkoda.dto.OrganizationRelatedObject;

public class SchedulerDto implements CanonicalObject, OrganizationRelatedObject {

    public SchedulerDto() {}

    public SchedulerDto(String cronExpression, String eventData, Long organizationId, boolean onMasterOnly, boolean async) {
        this.cronExpression = cronExpression;
        this.eventData = eventData;
        this.organizationId = organizationId;
        this.onMasterOnly = onMasterOnly;
        this.async = async;
    }

    public SchedulerDto(String cronExpression, String eventData, Long organizationId, boolean onMasterOnly) {
        this(cronExpression, eventData, organizationId, onMasterOnly, false);
    }

    public String cronExpression;
    public String eventData;
    public Long organizationId;
    public boolean onMasterOnly;
    private boolean async;

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public boolean isOnMasterOnly() {
        return onMasterOnly;
    }

    public void setOnMasterOnly(boolean onMasterOnly) {
        this.onMasterOnly = onMasterOnly;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    @Override
    public String notificationMessage() {
        return String.format("Scheduler: %s. With payload: %s.", cronExpression, eventData);
    }

}