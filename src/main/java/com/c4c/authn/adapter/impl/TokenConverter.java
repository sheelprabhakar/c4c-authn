package com.c4c.authn.adapter.impl;

import com.c4c.authn.core.entity.UserTokenEntity;
import com.c4c.authn.rest.resource.auth.JwtResponse;

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
        return new JwtResponse(tokenEntity.getAccessToken(), tokenEntity.getRefreshToken(), tokenEntity.getTenantId());
    }
}
