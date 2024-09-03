package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.rest.resource.RestAclResource;
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

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.REST_ACL_URL;
import static com.c4c.authz.rest.controller.RestAclController.BASE_URL;

/**
 * The type Rest acl controller.
 */
@Slf4j
@RestController()
@RequestMapping(BASE_URL)
public class RestAclController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = API_V1 + REST_ACL_URL;

    /**
     * Instantiates a new Rest acl controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    protected RestAclController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Find by id response entity.
     *
     * @param restAclId the rest acl id
     * @return the response entity
     */
    @GetMapping("/{restAclId}")
    public ResponseEntity<RestAclResource> findById(@PathVariable("restAclId") final UUID restAclId) {
        RestAclResource resource = this.getRestAdapterV1().findByIdRestAcl(restAclId);
        if (!Objects.isNull(resource)) {
            return ResponseEntity.ok().body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find by pagination response entity.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<PagedModelResponse<RestAclResource>> findByPagination(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "-1") final int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize) {
        if (pageSize > 0) {
            Page<RestAclResource> resources = this.getRestAdapterV1().findByPaginationRestAcl(pageIndex, pageSize);
            return ResponseEntity.ok().body(new PagedModelResponse<>(resources));
        } else {
            List<RestAclResource> resources = this.getRestAdapterV1().findAllRestAcl();
            return ResponseEntity.ok().body(new PagedModelResponse<>(new PageImpl<>(resources)));
        }
    }

    /**
     * Create response entity.
     *
     * @param restAclResource the rest acl resource
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<RestAclResource> create(final @RequestBody @Validated RestAclResource restAclResource) {
        restAclResource.setTenantId(CurrentUserContext.getCurrentTenantId());
        RestAclResource resource = this.getRestAdapterV1().createRestAcl(restAclResource);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId())).body(resource);
    }

    /**
     * Update response entity.
     *
     * @param restAclResource the rest acl resource
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<RestAclResource> update(final @RequestBody @Validated RestAclResource restAclResource) {
        restAclResource.setTenantId(CurrentUserContext.getCurrentTenantId());
        RestAclResource resource = this.getRestAdapterV1().updateRestAcl(restAclResource);
        return ResponseEntity.ok().body(resource);
    }

    /**
     * Delete by id response entity.
     *
     * @param restAclId the rest acl id
     * @return the response entity
     */
    @DeleteMapping("/{restAclId}")
    public ResponseEntity<Void> deleteById(@PathVariable("restAclId") final UUID restAclId) {
        this.getRestAdapterV1().deleteByIdRestAcl(restAclId);
        return ResponseEntity.noContent().build();
    }
}
