package com.c4c.authz.common;

/**
 * The type Constants.
 */
public final class Constants {
    /**
     * The constant AUTHORITIES.
     */
    public static final String AUTHORITIES = "authorities";
    /**
     * The constant IS_CLIENT_CRED.
     */
    public static final String IS_CLIENT_CRED = "isClientCred";
    /**
     * The constant CLIENT_CREDENTIALS.
     */
    public static final String CLIENT_CREDENTIALS ="client_credentials";
    /**
     * The constant POLICY.
     */
    public static final String POLICY = "POLICY";
    /**
     * The constant POLICY_URL.
     */
    public static final String POLICY_URL = "/v1/api/{tenantId}/policy/**";
    /**
     * The constant ANT_POLICY_URL.
     */
    public static final String ANT_POLICY_URL = "/v1/api/**/policy/**";
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
     * The constant REST_ACL_URL.
     */
    public static final String REST_ACL_URL = "/api/restAcl";

    /**
     * The constant CLIENT_URL.
     */
    public static final String CLIENT_URL = "/api/client";

    /**
     * The constant CLIENT_CRED_ROLE_NAME.
     */
    public static final String CLIENT_CRED_ROLE_NAME = "CLIENT_USER";

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
     * The constant ROLE_REST_ACL_URL.
     */
    public static final String ROLE_REST_ACL_URL = "/api/role/{roleId}/restAcl";

    /**
     * The constant IDEMPOTENCY_CACHE.
     */
    public static final String IDEMPOTENCY_CACHE = "idempotencyCache";
    /**
     * The constant ITEM_CACHE.
     */
    public static final String ITEM_CACHE = "itemCache";

    /**
     * The constant BEARER.
     */
    public static final String BEARER = "Bearer";
    /**
     * The constant INVALID_TOKEN.
     */
    public static final String INVALID_TOKEN = "Invalid token";

    /**
     * Instantiates a new Constants.
     */
    private Constants() {

    }
}
