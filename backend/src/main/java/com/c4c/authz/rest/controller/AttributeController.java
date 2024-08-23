package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.rest.resource.AttributeResource;
import com.c4c.authz.rest.resource.PagedModelResponse;
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
import static com.c4c.authz.common.Constants.ATTRIBUTE_URL;
import static com.c4c.authz.rest.controller.AttributeController.BASE_URL;

/**
 * The type Attribute controller.
 */
@Slf4j
@RestController()
@RequestMapping(BASE_URL)
public class AttributeController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = API_V1 + ATTRIBUTE_URL;

    /**
     * Instantiates a new Attribute controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    protected AttributeController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Find by id response entity.
     *
     * @param attributeId the attribute id
     * @return the response entity
     */
    @GetMapping("/{attributeId}")
    public ResponseEntity<AttributeResource> findById(@PathVariable("attributeId") final UUID attributeId) {
        AttributeResource resource = this.getRestAdapterV1().findByIdAttribute(attributeId);
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
    public ResponseEntity<PagedModelResponse<AttributeResource>> findByPagination(
            @RequestParam(value = "pageNo", required = false, defaultValue = "-1") final int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize) {
        if (pageSize > 0) {
            Page<AttributeResource> resources = this.getRestAdapterV1().findByPaginationAttribute(pageNo, pageSize);
            return ResponseEntity.ok().body(new PagedModelResponse<>(resources));
        } else {
            List<AttributeResource> resources = this.getRestAdapterV1().findAllAttribute();
            return ResponseEntity.ok().body(new PagedModelResponse<>(new PageImpl<>(resources)));
        }
    }

    /**
     * Create response entity.
     *
     * @param attributeResource the attribute resource
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<AttributeResource> create(final @RequestBody @Validated AttributeResource attributeResource) {
        attributeResource.setTenantId(CurrentUserContext.getCurrentTenant());
        AttributeResource resource = this.getRestAdapterV1().createAttribute(attributeResource);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId())).body(resource);
    }

    /**
     * Update response entity.
     *
     * @param attributeResource the attribute resource
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<AttributeResource> update(final @RequestBody @Validated AttributeResource attributeResource) {
        attributeResource.setTenantId(CurrentUserContext.getCurrentTenant());
        AttributeResource resource = this.getRestAdapterV1().updateAttribute(attributeResource);
        return ResponseEntity.ok().body(resource);
    }

    /**
     * Delete by id response entity.
     *
     * @param attributeId the attribute id
     * @return the response entity
     */
    @DeleteMapping("/{attributeId}")
    public ResponseEntity<Void> deleteById(@PathVariable("attributeId") final UUID attributeId) {
        this.getRestAdapterV1().deleteByIdAttribute(attributeId);
        return ResponseEntity.noContent().build();
    }
}
