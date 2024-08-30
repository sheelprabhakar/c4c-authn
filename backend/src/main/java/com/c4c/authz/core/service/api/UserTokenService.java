package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.UserTokenEntity;
import java.util.UUID;

/**
 * The interface User token service.
 */
public interface UserTokenService {
  /**
   * Gets by id.
   *
   * @param id the id
   * @return the by id
   */
  UserTokenEntity getById(UUID id);

  /**
   * Update user token entity.
   *
   * @param userId       the user id
   * @param tenantId     the tenant id
   * @param token        the token
   * @param refreshToken the refresh token
   * @return the user token entity
   */
  UserTokenEntity update(UUID userId, UUID tenantId, String token, String refreshToken);

  /**
   * Delete by id.
   *
   * @param userId the user id
   */
  void deleteById(UUID userId);
}
