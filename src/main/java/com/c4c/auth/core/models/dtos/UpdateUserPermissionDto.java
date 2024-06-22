package com.c4c.auth.core.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(name = "UpdateUserPermissionParam", description = "Parameters required to update user permissions")
@Accessors(chain = true)
@Setter
@Getter
public class UpdateUserPermissionDto {
  @Schema(description = "Array of permissions to give or remove to an user", required = true)
  @NotEmpty(message = "The field must have at least one item")
  private String[] permissions;
}
