package com.c4c.auth.core.models.dtos;

import com.c4c.auth.core.constraints.Exists;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(name = "ForgotPasswordParam", description = "Parameters required to request a reset link")
@Exists.List({
    @Exists(property = "email", repository = "UserRepository", message = "This email doesn't exists in the db!")
})
@Accessors(chain = true)
@Setter
@Getter
public class ForgotPasswordDto {
  @Schema(description = "The email address to sent the link to", required = true)
  @Email(message = "Email address is not valid")
  @NotBlank(message = "The email address is required")
  private String email;
}
