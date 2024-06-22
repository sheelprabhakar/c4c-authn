package com.c4c.auth.core.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The type UpdateRolePermissionDto.
 */
@Schema(name = "UpdateRolePermissionParam", description = "Parameters required to update role permissions")
@Accessors(chain = true)
@Setter
@Getter
public class UpdateRolePermissionDto {
  @Schema(description = "Array of permissions to give or remove to a role", required = true)
  @NotEmpty(message = "The field must have at least one item")
  private String[] permissions;
}
