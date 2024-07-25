package com.c4c.authn.config.tenant;

import java.util.UUID;

/**
 * The type Current user context.
 */
public class CurrentUserContext {
    /**
     * The constant currentTenant.
     */
    private static final ThreadLocal<UUID> currentTenant = new InheritableThreadLocal<>();
    /**
     * The constant currentUser.
     */
    private static final ThreadLocal<String> currentUser = new InheritableThreadLocal<>();

    /**
     * Instantiates a new Current user context.
     */
    private CurrentUserContext() {
    }

    /**
     * Gets current tenant.
     *
     * @return the current tenant
     */
    public static UUID getCurrentTenant() {
        return currentTenant.get();
    }

    /**
     * Sets current tenant.
     *
     * @param tenantId the tenant id
     */
    public static void setCurrentTenant(UUID tenantId) {
        currentTenant.set(tenantId);
    }


    /**
     * Sets current tenant.
     *
     * @param tenantId the tenant id
     */
    public static void setCurrentTenant(String tenantId) {
        setCurrentTenant(UUID.fromString(tenantId));
    }

    /**
     * Sets current user.
     *
     * @param userid the userid
     */
    public static void setCurrentUser(String userid) {
        currentUser.set(userid);
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public static String getCurrentUser() {
        return currentUser.get();
    }


    /**
     * Clear.
     */
    public static void clear() {
        currentTenant.remove();
        currentUser.remove();
    }
}