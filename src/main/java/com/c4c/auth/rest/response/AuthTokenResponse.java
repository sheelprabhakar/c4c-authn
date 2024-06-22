package com.c4c.auth.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The type AuthTokenResponse.
 */
@Accessors(chain = true)
@AllArgsConstructor
@Setter
@Getter
public class AuthTokenResponse {
  private String accessToken;

  private String refreshToken;

  private long expiresIn;
}


