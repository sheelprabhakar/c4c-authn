package com.c4c.auth.core.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(name = "RefreshTokenParam", description = "Parameters required to create or update user")
@Accessors(chain = true)
@Setter
@Getter
public class RefreshTokenDto {
  @Schema(description = "Refresh token to used to validate the user and generate a new token", required = true)
  @NotBlank(message = "The token is required")
  private String token;
}
