package com.c4c.authz.adapter.impl;

import static com.c4c.authz.common.Constants.BEARER;

import com.c4c.authz.core.entity.UserTokenEntity;
import com.c4c.authz.rest.resource.auth.JwtResponse;

/**
 * The type Token converter.
 */
public final class TokenConverter {
    /**
     * Instantiates a new Token converter.
     */
    private TokenConverter() {

    }

    /**
     * Auth success info to jwt response jwt response.
     *
     * @param tokenEntity the token entity
     * @return the jwt response
     */
    public static JwtResponse authSuccessInfoToJwtResponse(final UserTokenEntity tokenEntity) {
        return new JwtResponse(tokenEntity.getAccessToken(), tokenEntity.getRefreshToken(), tokenEntity.getTenantId(), BEARER);
    }
}
