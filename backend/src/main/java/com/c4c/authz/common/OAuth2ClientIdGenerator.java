package com.c4c.authz.common;


import java.security.SecureRandom;
import java.util.Base64;

/**
 * The type O auth 2 client id generator.
 */
public class OAuth2ClientIdGenerator {

  /**
   * The constant secureRandom.
   */
  private static final SecureRandom secureRandom = new SecureRandom();
  /**
   * The constant base64Encoder.
   */
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

  /**
   * Generate client id string.
   *
   * @return the string
   */
  public static String generateClientId() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

  /**
   * Generate client secret string.
   *
   * @return the string
   */
  public static String generateClientSecret() {
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}

