package com.c4c.authn.core.service;

import com.c4c.authn.core.entity.TenantEntity;
import java.util.List;
import java.util.UUID;

/**
 * The interface Tenant service.
 */
public interface TenantService {
  /**
   * Create tenant entity.
   *
   * @param map the map
   * @return the tenant entity
   */
  TenantEntity create(TenantEntity map);

  /**
   * Update tenant entity.
   *
   * @param tenantEntity the tenant entity
   * @return the tenant entity
   */
  TenantEntity update(TenantEntity tenantEntity);

  /**
   * Read tenant entity.
   *
   * @param tenantId the tenant id
   * @return the tenant entity
   */
  TenantEntity read(UUID tenantId);

  /**
   * Read all list.
   *
   * @return the list
   */
  List<TenantEntity> readAll();
}
