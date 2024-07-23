package com.c4c.authn.rest.controller;

import com.c4c.authn.adapter.api.RestAdapterV1;
import com.c4c.authn.config.tenant.TenantContext;
import com.c4c.authn.rest.resource.RestResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.c4c.authn.common.Constants.API_V1;
import static com.c4c.authn.common.Constants.REST_RESOURCE_URL;
import static com.c4c.authn.rest.controller.RestResourceController.BASE_URL;

/**
 * The type Rest resource controller.
 */
@Slf4j
@RestController()
@RequestMapping(BASE_URL)
public class RestResourceController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = API_V1 + REST_RESOURCE_URL;

    /**
     * Instantiates a new Rest resource controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    protected RestResourceController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Create response entity.
     *
     * @param restResource the rest resource
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<RestResource> create(final @RequestBody @Validated RestResource restResource) {
        restResource.setTenantId(TenantContext.getCurrentTenant());
        RestResource resource = this.getRestAdapterV1().createRestResource(restResource);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId())).body(resource);
    }
}
