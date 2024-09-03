package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.OauthTokenEntity;
import java.util.UUID;

/**
 * The interface Authentication service.
 */
public interface AuthenticationService {
    /**
     * Authenticate oauth token entity.
     *
     * @param username the username
     * @param password the password
     * @param isOtp    the is otp
     * @return the oauth token entity
     */
    OauthTokenEntity authenticate(String username, String password, boolean isOtp);

    /**
     * Logout.
     */
    void logout();

    /**
     * Refresh token oauth token entity.
     *
     * @param refreshToken the refresh token
     * @return the oauth token entity
     */
    OauthTokenEntity refreshToken(String refreshToken);

    /**
     * Authenticate oauth token entity.
     *
     * @param tenantId     the tenant id
     * @param clientId     the client id
     * @param clientSecret the client secret
     * @param grantType    the grant type
     * @return the oauth token entity
     */
    OauthTokenEntity authenticate(UUID tenantId, String clientId, String clientSecret, String grantType);
}
