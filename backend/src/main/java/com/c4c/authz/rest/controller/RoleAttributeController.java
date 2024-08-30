package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.rest.resource.RoleAttributeResource;
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
import static com.c4c.authz.common.Constants.ROLE_ATTRIBUTE_URL;
import static com.c4c.authz.rest.controller.RoleAttributeController.BASE_URL;

/**
 * The type Role attribute controller.
 */
@Slf4j
@RestController()
@RequestMapping(value = BASE_URL)
public class RoleAttributeController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = API_V1 + ROLE_ATTRIBUTE_URL;

    /**
     * Instantiates a new Role attribute controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    protected RoleAttributeController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Find by id response entity.
     *
     * @param roleId      the role id
     * @param attributeId the attribute id
     * @return the response entity
     */
    @GetMapping(value = "/{attributeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleAttributeResource> findById(@PathVariable(value = "roleId") final UUID roleId,
                                                          @PathVariable(value = "attributeId") final UUID attributeId) {
        RoleAttributeResource resource = this.getRestAdapterV1().findByIdRoleAttribute(roleId, attributeId);
        if (!Objects.isNull(resource)) {
            return ResponseEntity.ok().body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find by pagination response entity.
     *
     * @param roleId   the role id
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<PagedModelResponse<RoleAttributeResource>> findByPagination(
            @PathVariable(value = "roleId") final UUID roleId,
            @RequestParam(value = "pageNo", required = false, defaultValue = "-1") final int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize) {
        if (pageSize > 0) {
            Page<RoleAttributeResource> resources =
                    this.getRestAdapterV1().findByPaginationRoleAttribute(pageNo, pageSize);
            return ResponseEntity.ok().body(new PagedModelResponse<>(resources));
        } else {
            List<RoleAttributeResource> resources = this.getRestAdapterV1().findAllRoleAttribute();
            return ResponseEntity.ok().body(new PagedModelResponse<>(new PageImpl<>(resources)));
        }
    }

    /**
     * Create response entity.
     *
     * @param roleId                the role id
     * @param roleAttributeResource the role attribute resource
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleAttributeResource> create(
            @PathVariable(value = "roleId") final UUID roleId,
            final @RequestBody @Validated RoleAttributeResource roleAttributeResource) {
        RoleAttributeResource resource = this.getRestAdapterV1().createRoleAttribute(roleAttributeResource);
        return ResponseEntity.created(
                        URI.create(BASE_URL.replace("{roleId}", resource.getRoleId().toString()) + "/attributeId/"
                                + resource.getAttributeId()))
                .body(resource);
    }

    /**
     * Update response entity.
     *
     * @param roleId                the role id
     * @param roleAttributeResource the role attribute resource
     * @return the response entity
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleAttributeResource> update(
            @PathVariable(value = "roleId") final UUID roleId,
            final @RequestBody @Validated RoleAttributeResource roleAttributeResource) {
        RoleAttributeResource resource = this.getRestAdapterV1().updateRoleAttribute(roleAttributeResource);
        return ResponseEntity.ok().body(resource);
    }

    /**
     * Delete by id response entity.
     *
     * @param roleId      the role id
     * @param attributeId the attribute id
     * @return the response entity
     */
    @DeleteMapping(value = "/{attributeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable(value = "roleId") final UUID roleId,
                                           @PathVariable(value = "attributeId") final UUID attributeId) {
        this.getRestAdapterV1().deleteByIdRoleAttribute(roleId, attributeId);
        return ResponseEntity.noContent().build();
    }
}
