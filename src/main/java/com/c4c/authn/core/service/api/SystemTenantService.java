package com.c4c.authn.core.service.api;

import java.util.UUID;

public interface SystemTenantService {

    /**
     * Is system tenant boolean.
     *
     * @param tenantId the tenant id
     * @return the boolean
     */
    boolean isSystemTenant(UUID tenantId);
}
