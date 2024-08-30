package com.c4c.authz.common;


import java.security.SecureRandom;
import java.util.Base64;

/**
 * The type O auth 2 client id generator.
 */
public class OAuth2ClientIdGenerator {
    private OAuth2ClientIdGenerator() {
    }

    /**
   * The constant secureRandom.
   */
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();
  /**
   * The constant base64Encoder.
   */
  private static final Base64.Encoder BASE_64_ENCODER = Base64.getUrlEncoder().withoutPadding();

  /**
   * Generate client id string.
   *
   * @return the string
   */
  public static String generateClientId() {
        byte[] randomBytes = new byte[32];
        SECURE_RANDOM.nextBytes(randomBytes);
        return BASE_64_ENCODER.encodeToString(randomBytes);
    }

  /**
   * Generate client secret string.
   *
   * @return the string
   */
  public static String generateClientSecret() {
        byte[] randomBytes = new byte[64];
        SECURE_RANDOM.nextBytes(randomBytes);
        return BASE_64_ENCODER.encodeToString(randomBytes);
    }
}

