package com.c4c.authz.rest.resource.auth;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Jwt response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = -8091879091924046844L;

  /**
   * The Access token.
   */
  private String accessToken;

  /**
   * The Refresh token.
   */
  private String refreshToken;

  /**
   * The Tenant id.
   */
  private UUID tenantId;

  /**
   * The Token type.
   */
  private String tokenType;
}
