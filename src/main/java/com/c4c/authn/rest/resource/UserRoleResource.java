package com.c4c.authn.rest.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

/**
 * The type User role resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class UserRoleResource extends CommonResourceAttributes implements Serializable {

    /**
     * The Role id.
     */
    //@NotEmpty(message = "Role Id can not be empty.")
    private UUID roleId;

    /**
     * The User id.
     */
    //@NotEmpty(message = "User Id can not be empty.")
    private UUID userId;

    /**
     * The User resource.
     */
    private UserResource userResource;
    /**
     * The Role resource.
     */
    private RoleResource roleResource;
}
