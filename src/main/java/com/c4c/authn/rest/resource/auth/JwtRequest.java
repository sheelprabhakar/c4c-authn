package com.c4c.authn.rest.resource.auth;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Jwt request.
 */
@NoArgsConstructor
@Getter
@Setter
public class JwtRequest implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 5926468583005150707L;

    /**
     * The Username.
     */
    @NotBlank
  private String username;
    /**
     * The Password.
     */
    @NotBlank
  private String password;
    /**
     * The Is otp.
     */
    private boolean isOtp;
}
