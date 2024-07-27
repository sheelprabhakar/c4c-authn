package com.c4c.authn.core.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Tenant user entity id.
 */
@Getter
@Setter
public class TenantUserEntityId implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -9063780774369922132L;
    /**
     * The Tenant id.
     */
    private UUID tenantId;
    /**
     * The User id.
     */
    private UUID userId;

    /**
     * Instantiates a new Tenant user entity id.
     */
    public TenantUserEntityId() {

  }

    /**
     * Instantiates a new Tenant user entity id.
     *
     * @param tenantId the tenant id
     * @param userId   the user id
     */
    public TenantUserEntityId(final UUID tenantId, final UUID userId) {
    this.tenantId = tenantId;
    this.userId = userId;
  }
}
