package com.c4c.authz.common;


import java.security.SecureRandom;
import java.util.Base64;

/**
 * The type O auth 2 client id generator.
 */
public class OAuth2ClientIdGenerator {
    /**
     * Instantiates a new O auth 2 client id generator.
     */
    private OAuth2ClientIdGenerator() {
    }

    /**
     * The constant SECURE_RANDOM.
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    /**
     * The constant BASE_64_ENCODER.
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

