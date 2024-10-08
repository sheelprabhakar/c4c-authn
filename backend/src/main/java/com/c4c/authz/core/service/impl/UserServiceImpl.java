package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.repository.UserRepository;
import com.c4c.authz.core.service.api.UserService;
import jakarta.transaction.Transactional;
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
    private long otpValidDuration;
    /**
     * The User repository.
     */
    private final UserRepository userRepository;

    /**
     * The Password encoder.
     */
    private final PasswordEncoder passwordEncoder;


    /**
     * Instantiates a new User service.
     *
     * @param userRepository  the user repository
     * @param passwordEncoder the password encoder
     */
    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * Save user entity.
     *
     * @param userEntity the user entity
     * @return the user entity
     */
    @Override
    public UserEntity save(final UserEntity userEntity) {
        userEntity.setDeleted(false);
        if (userEntity.getId() == null) {
            userEntity.created(CurrentUserContext.getCurrentUser());
        } else {
            userEntity.updated(CurrentUserContext.getCurrentUser());
        }
        if (StringUtils.hasLength(userEntity.getPasswordHash())) {
            userEntity.setPasswordHash(this.passwordEncoder.encode(userEntity.getPasswordHash()));
        }
        /*if (Objects.isNull(userEntity.getRoles()) || userEntity.getRoles().size() == 0) {
            // Add default User Role
            // To-do
        }*/
        return this.userRepository.save(userEntity);
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
     * Find by user name user entity.
     *
     * @param userName the user name
     * @return the user entity
     */
    @Override
    public UserEntity findByUserName(final String userName) {
        return this.userRepository.findByUserName(userName);
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
     * Find by tenant id and user name user entity.
     *
     * @param tenantId the tenant id
     * @param userName the user name
     * @return the user entity
     */
    @Override
    public UserEntity findByTenantIdAndUserName(final UUID tenantId, final String userName) {
        return this.userRepository.findByTenantIdAndUserName(tenantId, userName).orElse(null);
    }

    /**
     * Delete.
     *
     * @param userEntity the user entity
     */
    @Override
    public void delete(final UserEntity userEntity) {
        this.userRepository.delete(userEntity);
    }
}
