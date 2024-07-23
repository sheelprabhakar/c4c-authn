package com.c4c.authn.rest.controller;

import com.c4c.authn.adapter.api.RestAdapterV1;
import com.c4c.authn.rest.resource.TenantResource;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.c4c.authn.common.Constants.API_V1;
import static com.c4c.authn.common.Constants.TENANT_URL;

/**
 * The type Tenant controller.
 */
@Slf4j
@RestController()
@RequestMapping(TenantController.BASE_URL)
public class TenantController extends BaseController {
  /**
   * The Base url.
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
   * Read response entity.
   *
   * @param tenantId the tenant id
   * @return the response entity
   */
  @GetMapping("/{tenantId}")
  public ResponseEntity<TenantResource> read(@PathVariable("tenantId") final UUID tenantId) {
    if (this.isSuperAdmin() || tenantId.equals(this.getTenantId())) {
      TenantResource resource = this.getRestAdapterV1().readTenant(tenantId);
      return ResponseEntity.ok()
          .body(resource);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  /**
   * Read response entity.
   *
   * @return the response entity
   */
  @GetMapping()
  public ResponseEntity<List<TenantResource>> read() {
    if (this.isSuperAdmin()) {
      List<TenantResource> resourceList = this.getRestAdapterV1().readTenants();
      return ResponseEntity.ok()
          .body(resourceList);
    } else {
      TenantResource resource = this.getRestAdapterV1().readTenant(this.getTenantId());
      return ResponseEntity.ok()
          .body(Collections.singletonList(resource));
    }
  }

  /**
   * Delete response entity.
   *
   * @param tenantId the tenant id
   * @return the response entity
   */
  @DeleteMapping("/{tenantId}")
  public ResponseEntity<Void> delete(@PathVariable("tenantId") final UUID tenantId) {
    if (this.isSuperAdmin()) {
      this.getRestAdapterV1().deleteTenant(tenantId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }
}
