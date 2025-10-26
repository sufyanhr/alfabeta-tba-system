package com.alfabeta.controller.api.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alfabeta.controller.api.v2.CRUDApiController;

/**
 * @deprecated This controller is part of the legacy API v1.
 *             Please migrate to {@link com.alfabeta.controller.api.v2.CRUDApiController}.
 *             Will be removed in future release.
 * @since 1.0.0
 */
@Deprecated(forRemoval = true, since = "1.0.0")
@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationControllerApi extends CRUDApiController {

    public OrganizationControllerApi() {
        super("ORGANIZATION_API_V1");
    }
}
