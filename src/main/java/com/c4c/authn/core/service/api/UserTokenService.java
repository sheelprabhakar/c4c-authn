package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.UserTokenEntity;
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
   * @param id           the id
   * @param token        the token
   * @param refreshToken
   * @return the user token entity
   */
  UserTokenEntity update(UUID id, String token, String refreshToken);

  /**
   * Delete by id.
   *
   * @param id the id
   */
  void deleteById(UUID id);
}
