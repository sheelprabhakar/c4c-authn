package com.c4c.authz.core.service.impl;

import com.c4c.authz.core.entity.OauthTokenEntity;
import com.c4c.authz.core.repository.OauthTokenRepository;
import com.c4c.authz.core.service.api.OauthTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * The type Oauth token service.
 */
@Service
@Slf4j
@Transactional
public class OauthTokenServiceImpl implements OauthTokenService {
    /**
     * The Oauth token repository.
     */
    private final OauthTokenRepository oauthTokenRepository;

    /**
     * Instantiates a new Oauth token service.
     *
     * @param oauthTokenRepository the oauth token repository
     */
    @Autowired
    public OauthTokenServiceImpl(final OauthTokenRepository oauthTokenRepository) {
        this.oauthTokenRepository = oauthTokenRepository;
    }

    /**
     * Gets by user id.
     *
     * @param userId the user id
     * @param date   the date
     * @return the by user id
     */
    @Override
    public List<OauthTokenEntity> getByUserId(final UUID userId, final Calendar date) {
        return this.oauthTokenRepository.findAllByUserId(userId, date );
    }

    /**
     * Gets by client id.
     *
     * @param clientId the client id
     * @param date     the date
     * @return the by client id
     */
    @Override
    public List<OauthTokenEntity> getByClientId(final UUID clientId, final Calendar date) {
        return this.oauthTokenRepository.findAllByClientId(clientId, date);
    }

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
    @Override
    @CacheEvict(value = "tokens", key = "#p0")
    public OauthTokenEntity addUserToken(final UUID userId, final UUID tenantId, final String token,
                                         final String refreshToken, final Calendar expiryTime) {
        OauthTokenEntity tokenEntity = OauthTokenEntity.builder().userId(userId).tenantId(tenantId).accessToken(token)
                .refreshToken(refreshToken).createdAt(Calendar.getInstance()).expiryTime(expiryTime).build();

        return this.oauthTokenRepository.save(tokenEntity);
    }

    /**
     * Add client token oauth token entity.
     *
     * @param clientId   the client id
     * @param tenantId   the tenant id
     * @param token      the token
     * @param expiryTime the expiry time
     * @return the oauth token entity
     */
    @CacheEvict(value = "tokens", key = "#p0")
    @Override
    public OauthTokenEntity addClientToken(final UUID clientId, final UUID tenantId, final String token,
                                          final Calendar expiryTime) {
        OauthTokenEntity tokenEntity =
                OauthTokenEntity.builder().clientId(clientId).tenantId(tenantId).accessToken(token)
                        .createdAt(Calendar.getInstance()).expiryTime(expiryTime).build();
        return this.oauthTokenRepository.save(tokenEntity);
    }

    /**
     * Delete by id.
     *
     * @param ids the ids
     */
    @Override
    public void deleteAllById(final List<UUID> ids) {
        this.oauthTokenRepository.deleteAllById(ids);
    }

}
