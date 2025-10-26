package com.alfabeta.controller.report;

import com.alfabeta.core.controller.frontendresource.AbstractFrontendResourceController;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.alfabeta.controller.common.URLConstants.*;


@Controller
@RequestMapping({_HTML + _QUERY_REPORT, _HTML_ORGANIZATION_ORGANIZATIONID + _QUERY_REPORT})
@Profile("!development")
public class BasicAIReportController extends AbstractFrontendResourceController {

    public Object sendPrompt(@PathVariable(name=ORGANIZATIONID, required = false) Long organizationId,
                             @RequestParam("prompt") String prompt,
                             @RequestParam("promptWebEndpoint") String promptWebEndpoint,
                             @RequestParam("conversationId") String conversationId,
                             @RequestParam(value = "channelId", required = false, defaultValue = "") String channelId,
                             @RequestParam(value = "model", required = false, defaultValue = "gpt-4-0613") String model,
                             @RequestParam(value = "temperature", required = false, defaultValue = "0.2") String temperature) {
        debug("[sendPrompt]");
        throw new NotImplementedException();
    }

}
