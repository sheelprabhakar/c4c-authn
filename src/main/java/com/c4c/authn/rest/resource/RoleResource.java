package com.c4c.authn.rest.resource;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

/**
 * The type Role resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class RoleResource extends CommonResourceAttributes implements Serializable {

  /**
   * The Id.
   */
  private UUID id;

  /**
   * The Tenant id.
   */
  private UUID tenantId;
  /**
   * The Name.
   */
  @NotEmpty(message = "Role name can not be empty.")
  @Size(max = 50, message = "Role name should be less than 50 characters.")
  private String name;
}
