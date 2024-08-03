package com.c4c.authn.common;

import java.util.UUID;

/**
 * The type Current user context.
 */
public final class CurrentUserContext {
    /**
     * The constant currentTenant.
     */
    private static final ThreadLocal<UUID> CURRENT_TENANT = new InheritableThreadLocal<>();
    /**
     * The constant currentUser.
     */
    private static final ThreadLocal<String> CURRENT_USER = new InheritableThreadLocal<>();

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
        return CURRENT_TENANT.get();
    }

    /**
     * Sets current tenant.
     *
     * @param tenantId the tenant id
     */
    public static void setCurrentTenant(final UUID tenantId) {
        CURRENT_TENANT.set(tenantId);
    }


    /**
     * Sets current tenant.
     *
     * @param tenantId the tenant id
     */
    public static void setCurrentTenant(final String tenantId) {
        setCurrentTenant(UUID.fromString(tenantId));
    }

    /**
     * Sets current user.
     *
     * @param userid the userid
     */
    public static void setCurrentUser(final String userid) {
        CURRENT_USER.set(userid);
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public static String getCurrentUser() {
        return CURRENT_USER.get();
    }


    /**
     * Clear.
     */
    public static void clear() {
        CURRENT_TENANT.remove();
        CURRENT_USER.remove();
    }
}
