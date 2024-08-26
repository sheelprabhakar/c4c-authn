package com.c4c.authz.common;

/**
 * The type Constants.
 */
public final class Constants {
    /**
     * The constant SYSTEM_TENANT.
     */
    public static final String SYSTEM_TENANT = "SYSTEM";
    /**
     * The constant SUPER_ADMIN.
     */
    public static final String SUPER_ADMIN = "SUPER_ADMIN";

    /**
     * The constant TENANT_ADMIN.
     */
    public static final String TENANT_ADMIN = "ADMIN";

    /**
     * The constant API_V1.
     */
    public static final String API_V1 = "/v1";
    /**
     * The constant TENANT_URL.
     */
    public static final String TENANT_URL = "/api/tenant";
    /**
     * The constant USER_URL.
     */
    public static final String USER_URL = "/api/user";
    /**
     * The constant AUTH_URL.
     */
    public static final String AUTH_URL = "/api/auth";
    /**
     * The constant ATTRIBUTE_URL.
     */
    public static final String ATTRIBUTE_URL = "/api/attribute";

    /**
     * The constant CLIENT_URL.
     */
    public static final String CLIENT_URL = "/api/client";

    /**
     * The constant LOOKUP_URL.
     */
    public static final String LOOKUP_URL = "/api/lookup";

    /**
     * The constant ROLE_URL.
     */
    public static final String ROLE_URL = "/api/role";

    /**
     * The constant USER_ROLE_URL.
     */
    public static final String USER_ROLE_URL = "/api/user/role";

    /**
     * The constant ROLE_ATTRIBUTE_URL.
     */
    public static final String ROLE_ATTRIBUTE_URL = "/api/role/{roleId}/attribute";

    /**
     * The constant IDEMPOTENCY_CACHE.
     */
    public static final String IDEMPOTENCY_CACHE = "idempotencyCache";
    /**
     * The constant ITEM_CACHE.
     */
    public static final String ITEM_CACHE = "itemCache";

    /**
     * Instantiates a new Constants.
     */
    private Constants() {

    }
}
