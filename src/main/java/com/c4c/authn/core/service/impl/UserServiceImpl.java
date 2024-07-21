package com.c4c.authn.core.service.impl;

import com.c4c.authn.common.SpringUtil;
import com.c4c.authn.core.entity.TenantUserEntity;
import com.c4c.authn.core.entity.UserEntity;
import com.c4c.authn.core.repository.TenantUserRepository;
import com.c4c.authn.core.repository.UserRepository;
import com.c4c.authn.core.service.api.TenantUserService;
import com.c4c.authn.core.service.api.UserService;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * The type User service.
 */
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
  /**
   * The Otp valid duration.
   */
  @Value("${society.management.otp.valid.duration:50000}")
  private final long otpValidDuration = 500000;
  /**
   * The User repository.
   */
  private final UserRepository userRepository;

  /**
   * The Password encoder.
   */
  private final PasswordEncoder passwordEncoder;

  /**
   * The Tenant user service.
   */
  private final TenantUserService tenantUserService;

  /**
   * Instantiates a new User service.
   *
   * @param userRepository             the user repository
   * @param tenantUserEntityRepository the tenant user entity repository
   * @param passwordEncoder            the password encoder
   * @param tenantUserService          the tenant user service
   */
  @Autowired
  public UserServiceImpl(final UserRepository userRepository,
                         final TenantUserRepository tenantUserEntityRepository,
                         final PasswordEncoder passwordEncoder,
                         final TenantUserService tenantUserService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tenantUserService = tenantUserService;
  }

  /**
   * Save user entity.
   *
   * @param userEntity the user entity
   * @return the user entity
   */
  @Override
  public UserEntity save(final UserEntity userEntity) {
    if (StringUtils.hasLength(userEntity.getPasswordHash())) {
      userEntity.setPasswordHash(this.passwordEncoder.encode(userEntity.getPasswordHash()));
    }
        /*if (Objects.isNull(userEntity.getRoles()) || userEntity.getRoles().size() == 0) {
            // Add default User Role
            // To-do
        }*/
    UserEntity entity = this.userRepository.save(userEntity);
    if (Objects.nonNull(SpringUtil.getTenantId())) {
      TenantUserEntity tenantUserEntity = this.tenantUserService
          .save(SpringUtil.getTenantId(), userEntity.getId());
    }
    return entity;
  }


  /**
   * Find by id user entity.
   *
   * @param id the id
   * @return the user entity
   */
  @Override
  public UserEntity findById(final UUID id) {
    return this.userRepository.findById(id).orElse(null);
  }

  /**
   * Find by email user entity.
   *
   * @param email the email
   * @return the user entity
   */
  @Override
  public UserEntity findByEmail(final String email) {
    return this.userRepository.findByEmail(email);
  }

  /**
   * Update user entity.
   *
   * @param userEntity the user entity
   * @return the user entity
   */
  @Override
  public UserEntity update(final UserEntity userEntity) {
    if (StringUtils.hasLength(userEntity.getPasswordHash())) {
      userEntity.setPasswordHash(this.passwordEncoder.encode(userEntity.getPasswordHash()));
    }
    return this.userRepository.save(userEntity);
  }

  /**
   * Clear otp.
   *
   * @param userEntity the user entity
   */
  @Override
  public void clearOTP(final UserEntity userEntity) {
    this.userRepository.clearOTP(userEntity.getId());
  }

  /**
   * Is otp required boolean.
   *
   * @param id the id
   * @return the boolean
   */
  @Override
  public boolean isOTPRequired(final UUID id) {
    UserEntity userEntity = this.userRepository.findById(id).orElse(null);
    if (userEntity == null) {
      return false;
    }
    return this.isOTPRequired(userEntity);
  }

  /**
   * Is otp required boolean.
   *
   * @param userEntity the user entity
   * @return the boolean
   */
  @Override
  public boolean isOTPRequired(final UserEntity userEntity) {
    if (userEntity.getOtp() == null) {
      return false;
    }

    long currentTimeInMillis = System.currentTimeMillis();
    long otpRequestedTimeInMillis = userEntity.getOtpAt().getTimeInMillis();

    if (otpRequestedTimeInMillis + this.otpValidDuration < currentTimeInMillis) {
      // OTP expires
      log.info("OTP expired");
      return false;
    }

    return true;
  }

  /**
   * Delete user entity.
   *
   * @param userEntity the user entity
   * @return the user entity
   */
  public UserEntity delete(final UserEntity userEntity) {
    return this.delete(userEntity);
  }
}
