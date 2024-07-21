package com.c4c.authn.core.service.impl;

import com.c4c.authn.core.entity.UserTokenEntity;
import com.c4c.authn.core.repository.UserTokenRepository;
import com.c4c.authn.core.service.api.UserTokenService;
import java.util.Calendar;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type User token service.
 */
@Service
@Slf4j
@Transactional
public class UserTokenServiceImpl implements UserTokenService {
  /**
   * The User token repository.
   */
  private final UserTokenRepository userTokenRepository;

  /**
   * Instantiates a new User token service.
   *
   * @param userTokenRepository the user token repository
   */
  @Autowired
  public UserTokenServiceImpl(final UserTokenRepository userTokenRepository) {
    this.userTokenRepository = userTokenRepository;
  }

  /**
   * Gets by id.
   *
   * @param id the id
   * @return the by id
   */
  @Override
  @Cacheable(value = "tokens", key = "#p0")
  public UserTokenEntity getById(final UUID id) {
    return this.userTokenRepository.findById(id).orElse(null);
  }

  /**
   * Update user token entity.
   *
   * @param id           the id
   * @param token        the token
   * @param refreshToken
   * @return the user token entity
   */
  @Override
  @CacheEvict(value = "tokens", key = "#p0")
  public UserTokenEntity update(final UUID id, final String token, final String refreshToken) {
    UserTokenEntity tokenEntity = this.userTokenRepository.findById(id).orElse(null);
    if (tokenEntity == null) {
      tokenEntity = new UserTokenEntity();
      tokenEntity.setUserId(id);
    }

    tokenEntity.setAccessToken(token);
    tokenEntity.setRefreshToken(refreshToken);
    tokenEntity.setUpdatedAt(Calendar.getInstance());

    return this.userTokenRepository.save(tokenEntity);
  }

  /**
   * Delete by id.
   *
   * @param id the id
   */
  @Override
  @CacheEvict(value = "tokens", key = "#id.toString()")
  public void deleteById(final UUID id) {
    this.userTokenRepository.deleteById(id);
  }

}
