package com.c4c.authz.core.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * The type Role attribute id.
 */
@Getter
public class RoleAttributeId implements Serializable {
    /**
     * The Role id.
     */
    private UUID roleId;
    /**
     * The Attribute id.
     */
    private UUID attributeId;

    /**
     * Instantiates a new Role attribute id.
     *
     * @param roleId      the role id
     * @param attributeId the attribute id
     */
    public RoleAttributeId(final UUID roleId, final UUID attributeId) {
        this.roleId = roleId;
        this.attributeId = attributeId;
    }

    /**
     * Instantiates a new Role attribute id.
     *
     * @param roleId      the role id
     * @param attributeId the attribute id
     */
    public RoleAttributeId(final String roleId, final String attributeId) {
        this.roleId = UUID.fromString(roleId);
        this.attributeId = UUID.fromString(attributeId);
    }

    /**
     * Instantiates a new Role attribute id.
     */
    public RoleAttributeId() {

    }
}
