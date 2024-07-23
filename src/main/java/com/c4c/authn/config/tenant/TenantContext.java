package com.c4c.authn.config.tenant;

import java.util.UUID;

public class TenantContext {
    private static final ThreadLocal<UUID> currentTenant = new InheritableThreadLocal<>();

    private TenantContext() {
    }

    public static UUID getCurrentTenant() {
        return currentTenant.get();
    }

    public static void setCurrentTenant(UUID tenantId) {
        currentTenant.set(tenantId);
    }

    public static void setCurrentTenant(String tenantId) {
        setCurrentTenant(UUID.fromString(tenantId));
    }

    public static void clear() {
        currentTenant.remove();
    }
}