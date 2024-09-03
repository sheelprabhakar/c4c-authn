package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.rest.resource.RoleRestAclResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
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
import static com.c4c.authz.common.Constants.ROLE_REST_ACL_URL;
import static com.c4c.authz.rest.controller.RoleRestAclController.BASE_URL;

/**
 * The type Role rest acl controller.
 */
@Slf4j
@RestController()
@RequestMapping(value = BASE_URL)
public class RoleRestAclController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = API_V1 + ROLE_REST_ACL_URL;

    /**
     * Instantiates a new Role rest acl controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    protected RoleRestAclController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Find by id response entity.
     *
     * @param roleId    the role id
     * @param restAclId the rest acl id
     * @return the response entity
     */
    @GetMapping(value = "/{restAclId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleRestAclResource> findById(@PathVariable(value = "roleId") final UUID roleId,
                                                        @PathVariable(value = "restAclId") final UUID restAclId) {
        RoleRestAclResource resource = this.getRestAdapterV1().findByIdRoleRestAcl(roleId, restAclId);
        if (!Objects.isNull(resource)) {
            return ResponseEntity.ok().body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find by pagination response entity.
     *
     * @param roleId    the role id
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<PagedModelResponse<RoleRestAclResource>> findByPagination(
            @PathVariable(value = "roleId") final UUID roleId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "-1") final int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize) {
        if (pageSize > 0) {
            Page<RoleRestAclResource> resources =
                    this.getRestAdapterV1().findByPaginationRoleRestAcl(pageIndex, pageSize);
            return ResponseEntity.ok().body(new PagedModelResponse<>(resources));
        } else {
            List<RoleRestAclResource> resources = this.getRestAdapterV1().findAllRoleRestAcl();
            return ResponseEntity.ok().body(new PagedModelResponse<>(new PageImpl<>(resources)));
        }
    }

    /**
     * Create response entity.
     *
     * @param roleId              the role id
     * @param roleRestAclResource the role rest acl resource
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleRestAclResource> create(
            @PathVariable(value = "roleId") final UUID roleId,
            final @RequestBody @Validated RoleRestAclResource roleRestAclResource) {
        RoleRestAclResource resource = this.getRestAdapterV1().createRoleRestAcl(roleRestAclResource);
        return ResponseEntity.created(
                        URI.create(BASE_URL.replace("{roleId}", resource.getRoleId().toString()) + "/restAclId/"
                                + resource.getRestAclId()))
                .body(resource);
    }

    /**
     * Update response entity.
     *
     * @param roleId              the role id
     * @param roleRestAclResource the role rest acl resource
     * @return the response entity
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleRestAclResource> update(
            @PathVariable(value = "roleId") final UUID roleId,
            final @RequestBody @Validated RoleRestAclResource roleRestAclResource) {
        RoleRestAclResource resource = this.getRestAdapterV1().updateRoleRestAcl(roleRestAclResource);
        return ResponseEntity.ok().body(resource);
    }

    /**
     * Delete by id response entity.
     *
     * @param roleId    the role id
     * @param restAclId the rest acl id
     * @return the response entity
     */
    @DeleteMapping(value = "/{restAclId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable(value = "roleId") final UUID roleId,
                                           @PathVariable(value = "restAclId") final UUID restAclId) {
        this.getRestAdapterV1().deleteByIdRoleRestAcl(roleId, restAclId);
        return ResponseEntity.noContent().build();
    }
}
