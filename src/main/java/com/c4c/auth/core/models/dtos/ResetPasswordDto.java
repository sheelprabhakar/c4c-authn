package com.c4c.auth.core.models.dtos;

import com.c4c.auth.core.constraints.FieldMatch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(name = "ResetPasswordParam", description = "Parameters required to reset password")
@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
@Accessors(chain = true)
@Setter
@Getter
public class ResetPasswordDto {
  @Schema(description = "The token included in the reset link", required = true)
  @NotBlank(message = "The token is required")
  private String token;

  @Schema(description = "New value of the password", required = true)
  @Size(min = 6, message = "Must be at least 6 characters")
  @NotBlank(message = "This field is required")
  private String password;

  @Schema(description = "Confirmation of the new value of the password", required = true)
  @NotBlank(message = "This field is required")
  private String confirmPassword;
}
