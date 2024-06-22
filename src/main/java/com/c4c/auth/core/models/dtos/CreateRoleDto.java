package com.c4c.auth.core.models.dtos;

import com.c4c.auth.core.models.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(name = "CreateRoleParam", description = "Parameters required to create role")
@Accessors(chain = true)
@Setter
@Getter
public class CreateRoleDto {
  @Schema(description = "Name of the role", required = true)
  @NotBlank(message = "The name is required")
  private String name;

  @Schema(description = "Description of the role")
  private String description;

  private boolean isDefault;

  public Role toRole() {
    return new Role()
        .setName(this.name)
        .setDescription(this.description)
        .setDefault(this.isDefault);
  }
}
