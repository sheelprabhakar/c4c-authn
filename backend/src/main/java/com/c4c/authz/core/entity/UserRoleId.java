package com.c4c.authz.core.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;

/**
 * The type User role id.
 */
@Getter
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
   * @param userId the user id
   * @param roleId the role id
   */
  public UserRoleId(final UUID userId, final UUID roleId) {
    this.roleId = roleId;
    this.userId = userId;
  }

  /**
   * Instantiates a new User role id.
   *
   * @param userId the user id
   * @param roleId the role id
   */
  public UserRoleId(final String userId, final String roleId) {
    this.roleId = UUID.fromString(roleId);
    this.userId = UUID.fromString(userId);
  }

  /**
   * Instantiates a new User role id.
   */
  public UserRoleId() {

  }
}
