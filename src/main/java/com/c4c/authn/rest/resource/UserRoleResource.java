package com.c4c.authn.rest.resource;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class UserRoleResource extends CommonResourceAttributes implements Serializable {

    /**
     * The Role id.
     */
    @NotNull(message = "Role Id can not be null.")
    private UUID roleId;

    /**
     * The User id.
     */
    @NotNull(message = "User Id can not be null.")
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
