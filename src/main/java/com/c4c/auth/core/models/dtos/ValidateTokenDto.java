package com.c4c.auth.core.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The type ValidateTokenDto.
 */
@Schema(name = "ValidateTokenParam", description = "Parameters required to perform a token validation")
@Accessors(chain = true)
@Setter
@Getter
public class ValidateTokenDto {
  @Schema(description = "Token to validate", required = true)
  @NotBlank(message = "The token is required")
  private String token;
}
