package com.c4c.auth.core.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The type UpdateRoleDto.
 */
@Schema(name = "UpdateRoleParam", description = "Parameters required to update the roles of an user")
@Accessors(chain = true)
@Setter
@Getter
public class UpdateRoleDto {
  @Schema(description = "User identifier", required = true)
  @NotBlank(message = "The userId is required")
  private String userId;

  @Schema(description = "Array of roles to give to an user", required = true)
  @NotEmpty(message = "The field must have at least one item")
  private String[] roles;
}
