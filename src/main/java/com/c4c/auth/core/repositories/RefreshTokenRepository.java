package com.c4c.auth.core.repositories;

import com.c4c.auth.core.models.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface RefreshTokenRepository.
 */
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
  /**
   * Find by value refresh token.
   *
   * @param value the value
   * @return the refresh token
   */
  RefreshToken findByValue(String value);
}