package com.c4c.authz.common;

import java.util.UUID;

/**
 * The type Current user context.
 */
public final class CurrentUserContext {
  /**
   * The constant CURRENT_TENANT.
   */
  private static final ThreadLocal<UUID> CURRENT_TENANT = new InheritableThreadLocal<>();
  /**
   * The constant CURRENT_USER.
   */
  private static final ThreadLocal<String> CURRENT_USER = new InheritableThreadLocal<>();

  /**
   * The constant TOKEN_ID.
   */
  private static final ThreadLocal<UUID> TOKEN_ID = new InheritableThreadLocal<>();


  /**
   * Instantiates a new Current user context.
   */
  private CurrentUserContext() {
  }

  /**
   * Get current toke id uuid.
   *
   * @return the uuid
   */
  public static UUID getCurrentTokeId(){
    return TOKEN_ID.get();
  }

  /**
   * Set current toke id.
   *
   * @param uuid the uuid
   */
  public static void setCurrentTokeId(UUID uuid){
    TOKEN_ID.set(uuid);
  }

  /**
   * Gets current tenant id.
   *
   * @return the current tenant id
   */
  public static UUID getCurrentTenantId() {
    return CURRENT_TENANT.get();
  }


  /**
   * Sets current tenant id.
   *
   * @param tenantId the tenant id
   */
  public static void setCurrentTenantId(final UUID tenantId) {
    CURRENT_TENANT.set(tenantId);
  }


  /**
   * Sets current tenant id.
   *
   * @param tenantId the tenant id
   */
  public static void setCurrentTenantId(final String tenantId) {
    setCurrentTenantId(UUID.fromString(tenantId));
  }

  /**
   * Sets current user.
   *
   * @param userName the user name
   */
  public static void setCurrentUser(final String userName) {
    CURRENT_USER.set(userName);
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
