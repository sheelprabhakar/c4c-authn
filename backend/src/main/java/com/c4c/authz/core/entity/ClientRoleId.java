package com.c4c.authz.core.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;

/**
 * The type Client role id.
 */
@Getter
public class ClientRoleId implements Serializable {
  /**
   * The Role id.
   */
  private UUID roleId;
  /**
   * The Client id.
   */
  private UUID clientId;

  /**
   * Instantiates a new Client role id.
   *
   * @param clientId the client id
   * @param roleId   the role id
   */
  public ClientRoleId(final UUID clientId, final UUID roleId) {
    this.roleId = roleId;
    this.clientId = clientId;
  }

  /**
   * Instantiates a new Client role id.
   *
   * @param clientId the client id
   * @param roleId   the role id
   */
  public ClientRoleId(final String clientId, final String roleId) {
    this.roleId = UUID.fromString(roleId);
    this.clientId = UUID.fromString(clientId);
  }

  /**
   * Instantiates a new Client role id.
   */
  public ClientRoleId() {

  }
}
