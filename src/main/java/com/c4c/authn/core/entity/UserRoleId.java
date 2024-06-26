package com.c4c.authn.core.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * The type User role id.
 */
public class UserRoleId implements Serializable {
  /**
   * The Role id.
   */
  private UUID roleId;
  /**
   * The User id.
   */
  private UUID userId;

  /**
   * Instantiates a new User role id.
   *
   * @param roleId the role id
   * @param userId the user id
   */
  public UserRoleId(final UUID roleId, final UUID userId) {
    this.roleId = roleId;
    this.userId = userId;
  }

  /**
   * Instantiates a new User role id.
   */
  public UserRoleId() {

  }
}
