package com.c4c.authn.rest.controller;

import com.c4c.authn.adapter.api.RestAdapterV1;
import com.c4c.authn.config.tenant.CurrentUserContext;
import com.c4c.authn.rest.resource.RestResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
     * Find by id response entity.
     *
     * @param restResourceId the rest resource id
     * @return the response entity
     */
    @GetMapping("/{restResourceId}")
    public ResponseEntity<RestResource> findById(@PathVariable("restResourceId") final UUID restResourceId) {
        RestResource resource = this.getRestAdapterV1().findByIdRestResource(restResourceId);
        if(!Objects.isNull(resource)) {
            return ResponseEntity.ok().body(resource);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find by pagination response entity.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<Page<RestResource>> findByPagination(
            @RequestParam(value = "pageNo", required = false, defaultValue = "-1") final int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize) {
        if (pageSize > 0) {
            Page<RestResource> resources = this.getRestAdapterV1().findByPaginationRestResource(pageNo, pageSize);
            return ResponseEntity.ok().body(resources);
        } else {
            List<RestResource> resources = this.getRestAdapterV1().findAllRestResource();
            return ResponseEntity.ok().body(new PageImpl<>(resources));
        }
    }

    /**
     * Create response entity.
     *
     * @param restResource the rest resource
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<RestResource> create(final @RequestBody @Validated RestResource restResource) {
        restResource.setTenantId(CurrentUserContext.getCurrentTenant());
        RestResource resource = this.getRestAdapterV1().createRestResource(restResource);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId())).body(resource);
    }

    /**
     * Create response entity.
     *
     * @param restResource the rest resource
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<RestResource> update(final @RequestBody @Validated RestResource restResource) {
        restResource.setTenantId(CurrentUserContext.getCurrentTenant());
        RestResource resource = this.getRestAdapterV1().updateRestResource(restResource);
        return ResponseEntity.ok().body(resource);
    }

    @DeleteMapping("/{restResourceId}")
    public ResponseEntity<Void> deleteById(@PathVariable("restResourceId") final UUID restResourceId) {
        this.getRestAdapterV1().deleteByIdRestResource(restResourceId);
        return ResponseEntity.noContent().build();
    }
}
