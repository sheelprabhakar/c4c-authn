package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.UserTokenEntity;

/**
 * The interface Authentication service.
 */
public interface AuthenticationService {
    /**
     * Authenticate user token entity.
     *
     * @param username the username
     * @param password the password
     * @param isOtp    the is otp
     * @return the user token entity
     */
    UserTokenEntity authenticate(String username, String password, boolean isOtp);

    /**
     * Logout.
     */
    void logout();

    /**
     * Refresh token user token entity.
     *
     * @param refreshToken the refresh token
     * @return the user token entity
     */
    UserTokenEntity refreshToken(String refreshToken);
}
