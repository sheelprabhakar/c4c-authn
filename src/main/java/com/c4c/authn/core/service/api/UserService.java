package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.UserEntity;
import java.util.UUID;

/**
 * The interface User service.
 */
public interface UserService {
  /**
   * Save user entity.
   *
   * @param userEntity the user entity
   * @return the user entity
   */
  UserEntity save(UserEntity userEntity);

  /**
   * Find by id user entity.
   *
   * @param id the id
   * @return the user entity
   */
  UserEntity findById(UUID id);

  /**
   * Find by email user entity.
   *
   * @param email the email
   * @return the user entity
   */
  UserEntity findByEmail(String email);

  /**
   * Update user entity.
   *
   * @param userEntity the user entity
   * @return the user entity
   */
  UserEntity update(UserEntity userEntity);

  /**
   * Clear otp.
   *
   * @param userEntity the user entity
   */
  void clearOTP(UserEntity userEntity);

  /**
   * Is otp required boolean.
   *
   * @param id the id
   * @return the boolean
   */
  boolean isOTPRequired(UUID id);

  /**
   * Is otp required boolean.
   *
   * @param userEntity the user entity
   * @return the boolean
   */
  boolean isOTPRequired(UserEntity userEntity);
}
