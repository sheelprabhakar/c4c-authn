package com.c4c.authz.core.service.api;

import java.util.UUID;

/**
 * The interface System tenant service.
 */
public interface SystemTenantService {

  /**
   * Is system tenant boolean.
   *
   * @param tenantId the tenant id
   * @return the boolean
   */
  boolean isSystemTenant(UUID tenantId);
}
