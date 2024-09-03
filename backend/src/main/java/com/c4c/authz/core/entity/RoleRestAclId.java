package com.c4c.authz.core.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;

/**
 * The type Role rest acl id.
 */
@Getter
public class RoleRestAclId implements Serializable {
    /**
     * The Role id.
     */
    private UUID roleId;
    /**
     * The Rest acl id.
     */
    private UUID restAclId;

    /**
     * Instantiates a new Role rest acl id.
     *
     * @param roleId    the role id
     * @param restAclId the rest acl id
     */
    public RoleRestAclId(final UUID roleId, final UUID restAclId) {
        this.roleId = roleId;
        this.restAclId = restAclId;
    }

    /**
     * Instantiates a new Role rest acl id.
     *
     * @param roleId    the role id
     * @param restAclId the rest acl id
     */
    public RoleRestAclId(final String roleId, final String restAclId) {
        this.roleId = UUID.fromString(roleId);
        this.restAclId = UUID.fromString(restAclId);
    }

    /**
     * Instantiates a new Role rest acl id.
     */
    public RoleRestAclId() {

    }
}
