package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.rest.resource.RoleResource;
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
import static com.c4c.authz.common.Constants.ROLE_URL;
import static com.c4c.authz.rest.controller.RoleController.BASE_URL;

/**
 * The type Role controller.
 */
@Slf4j
@RestController()
@RequestMapping(BASE_URL)
public class RoleController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = API_V1 + ROLE_URL;

    /**
     * Instantiates a new Role controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    protected RoleController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Find by id response entity.
     *
     * @param roleId the role id
     * @return the response entity
     */
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResource> findById(@PathVariable("roleId") final UUID roleId) {
        RoleResource resource = this.getRestAdapterV1().findByIdRole(roleId);
        if (!Objects.isNull(resource)) {
            return ResponseEntity.ok().body(resource);
        } else {
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
    public ResponseEntity<PagedModelResponse<RoleResource>> findByPagination(
            @RequestParam(value = "pageNo", required = false, defaultValue = "-1") final int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize) {
        if (pageSize > 0) {
            Page<RoleResource> resources = this.getRestAdapterV1().findByPaginationRole(pageNo, pageSize);
            return ResponseEntity.ok().body(new PagedModelResponse<>(resources));
        } else {
            List<RoleResource> resources = this.getRestAdapterV1().findAllRole();
            return ResponseEntity.ok().body(new PagedModelResponse<>(new PageImpl<>(resources)));
        }
    }

    /**
     * Create response entity.
     *
     * @param role the role
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<RoleResource> create(final @RequestBody @Validated RoleResource role) {
        role.setTenantId(CurrentUserContext.getCurrentTenantId());
        RoleResource resource = this.getRestAdapterV1().createRole(role);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId())).body(resource);
    }

    /**
     * Update response entity.
     *
     * @param role the role
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<RoleResource> update(final @RequestBody @Validated RoleResource role) {
        role.setTenantId(CurrentUserContext.getCurrentTenantId());
        RoleResource resource = this.getRestAdapterV1().updateRole(role);
        return ResponseEntity.ok().body(resource);
    }

    /**
     * Delete by id response entity.
     *
     * @param roleId the role id
     * @return the response entity
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteById(@PathVariable("roleId") final UUID roleId) {
        this.getRestAdapterV1().deleteByIdRole(roleId);
        return ResponseEntity.noContent().build();
    }
}
