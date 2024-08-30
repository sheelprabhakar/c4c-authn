package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.TENANT_URL;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.rest.resource.TenantResource;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Tenant controller.
 */
@Slf4j
@RestController()
@RequestMapping(TenantController.BASE_URL)
public class TenantController extends BaseController {
  /**
   * The constant BASE_URL.
   */
  static final String BASE_URL = API_V1 + TENANT_URL;

  /**
   * Instantiates a new Tenant controller.
   *
   * @param restAdapterV1 the rest adapter v 1
   */
  @Autowired
  public TenantController(final RestAdapterV1 restAdapterV1) {
    super(restAdapterV1);
  }

  /**
   * Create response entity.
   *
   * @param tenantResource the tenant resource
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<TenantResource> create(@Valid @RequestBody final TenantResource tenantResource) {
    TenantResource resource = this.getRestAdapterV1().createTenant(tenantResource);
    return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId()))
        .body(resource);
  }

  /**
   * Update response entity.
   *
   * @param tenantResource the tenant resource
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<TenantResource> update(@Valid @RequestBody final TenantResource tenantResource) {
    TenantResource resource = this.getRestAdapterV1().updateTenant(tenantResource);
    return ResponseEntity.ok()
        .body(resource);
  }

  /**
   * Find by id response entity.
   *
   * @param tenantId the tenant id
   * @return the response entity
   */
  @GetMapping("/{tenantId}")
  public ResponseEntity<TenantResource> findById(@PathVariable("tenantId") final UUID tenantId) {
    if (this.isSuperAdmin() || tenantId.equals(this.getTenantId())) {
      TenantResource resource = this.getRestAdapterV1().findByIdTenant(tenantId);
      return ResponseEntity.ok()
          .body(resource);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
  public ResponseEntity<PagedModelResponse<TenantResource>> findByPagination(
          @RequestParam(value = "pageNo", required = false, defaultValue = "-1") final int pageNo,
          @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize) {
    if (pageSize > 0) {
      Page<TenantResource> resources =
              this.getRestAdapterV1().findByPaginationTenant(pageNo, pageSize);
      return ResponseEntity.ok().body(new PagedModelResponse<>(resources));
    } else {
      List<TenantResource> resources = this.getRestAdapterV1().findAllTenant();
      return ResponseEntity.ok().body(new PagedModelResponse<>(new PageImpl<>(resources)));
    }
  }

  /**
   * Delete by id response entity.
   *
   * @param tenantId the tenant id
   * @return the response entity
   */
  @DeleteMapping("/{tenantId}")
  public ResponseEntity<Void> deleteById(@PathVariable("tenantId") final UUID tenantId) {
    if (this.isSuperAdmin()) {
      this.getRestAdapterV1().deleteByIdTenant(tenantId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }
}
