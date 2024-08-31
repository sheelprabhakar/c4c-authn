package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.OauthTokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * The interface Oauth token repository.
 */
@Repository
public interface OauthTokenRepository extends CrudRepository<OauthTokenEntity, UUID> {
    /**
     * Find all by user id list.
     *
     * @param userId the user id
     * @param date   the date
     * @return the list
     */
    @Query("SELECT o FROM OauthTokenEntity o WHERE o.userId =:userId AND o.expiryTime > :date")
    List<OauthTokenEntity> findAllByUserId(UUID userId, Calendar date);

    /**
     * Find all by client id list.
     *
     * @param clientId the client id
     * @param date     the date
     * @return the list
     */
    @Query("SELECT o FROM OauthTokenEntity o WHERE o.clientId =:clientId AND o.expiryTime > :date")
    List<OauthTokenEntity> findAllByClientId(UUID clientId, Calendar date);
}
