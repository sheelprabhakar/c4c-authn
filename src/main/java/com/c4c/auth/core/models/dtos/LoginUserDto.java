package com.c4c.auth.core.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The type LoginUserDto.
 */
@Schema(name = "LoginUserParam", description = "Parameters required to login user")
@Accessors(chain = true)
@Setter
@Getter
public class LoginUserDto {
  @Schema(description = "User email address", required = true)
  @Email(message = "Email address is not valid")
  @NotBlank(message = "The email address is required")
  private String email;

  @Schema(description = "User password (Min character: 6)", required = true)
  @Size(min = 6, message = "Must be at least 6 characters")
  private String password;
}
