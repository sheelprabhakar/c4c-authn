package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.UserEntity;
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
     * Find by user name user entity.
     *
     * @param userName the user name
     * @return the user entity
     */
    UserEntity findByUserName(String userName);

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

    /**
     * Find by tenant id and user name user entity.
     *
     * @param tenantId the tenant id
     * @param userName the user name
     * @return the user entity
     */
    UserEntity findByTenantIdAndUserName(UUID tenantId, String userName);

    /**
     * Delete.
     *
     * @param userEntity the user entity
     */
    void delete(UserEntity userEntity);
}
