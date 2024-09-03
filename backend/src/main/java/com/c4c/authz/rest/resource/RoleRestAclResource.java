package com.c4c.authz.rest.resource;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Role rest acl resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class RoleRestAclResource extends CommonResourceAttributes implements Serializable {

  /**
   * The Role id.
   */
  @NotNull(message = "Role Id can not be null.")
    private UUID roleId;

  /**
   * The Rest acl id.
   */
  @NotNull(message = "Attribute Id can not be null.")
    private UUID restAclId;

  /**
   * The Can create.
   */
  private boolean canCreate;

  /**
   * The Can read.
   */
  private boolean canRead;

  /**
   * The Can update.
   */
  private boolean canUpdate;

  /**
   * The Can delete.
   */
  private boolean canDelete;
  /**
   * The Rest acl resource.
   */
  private RestAclResource restAclResource;
  /**
   * The Role resource.
   */
  private RoleResource roleResource;
}
