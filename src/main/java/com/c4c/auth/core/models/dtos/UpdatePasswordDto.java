package com.c4c.auth.core.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(name = "UpdatePasswordParam", description = "Parameters required to update the password")
@Accessors(chain = true)
@Setter
@Getter
public class UpdatePasswordDto {
  @Schema(description = "Current user password", required = true)
  @Size(min = 6, message = "Must be at least 6 characters")
  @NotBlank(message = "This field is required")
  private String currentPassword;

  @Schema(description = "New user password", required = true)
  @Size(min = 6, message = "Must be at least 6 characters")
  @NotBlank(message = "This field is required")
  private String newPassword;
}
