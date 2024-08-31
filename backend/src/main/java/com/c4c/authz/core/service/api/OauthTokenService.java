package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.OauthTokenEntity;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * The interface Oauth token service.
 */
public interface OauthTokenService {

    /**
     * Gets by user id.
     *
     * @param userId the user id
     * @param date   the date
     * @return the by user id
     */
    List<OauthTokenEntity> getByUserId(UUID userId, Calendar date);

    /**
     * Gets by client id.
     *
     * @param clientId the client id
     * @param date     the date
     * @return the by client id
     */
    List<OauthTokenEntity> getByClientId(UUID clientId, Calendar date);

    /**
     * Add user token oauth token entity.
     *
     * @param userId       the user id
     * @param tenantId     the tenant id
     * @param token        the token
     * @param refreshToken the refresh token
     * @param expiryTime   the expiry time
     * @return the oauth token entity
     */
    OauthTokenEntity addUserToken(UUID userId, UUID tenantId, String token, String refreshToken, final Calendar expiryTime);

    /**
     * Add client token oauth token entity.
     *
     * @param clientId   the client id
     * @param tenantId   the tenant id
     * @param token      the token
     * @param expiryTime the expiry time
     * @return the oauth token entity
     */
    OauthTokenEntity addClientToken(UUID clientId, UUID tenantId, String token, Calendar expiryTime);

    /**
     * Delete all by id.
     *
     * @param ids the ids
     */
    void deleteAllById(List<UUID> ids);
}
