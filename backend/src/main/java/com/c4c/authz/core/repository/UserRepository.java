package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
  /**
   * Clear otp int.
   *
   * @param id the id
   * @return the int
   */
  @Modifying
  @Query("update users ue set ue.otp = NULL where ue.id = :id")
  int clearOTP(@Param("id") UUID id);

  /**
   * Find by email user entity.
   *
   * @param email the email
   * @return the user entity
   */
  UserEntity findByEmail(String email);

  /**
   * Find by mobile user entity.
   *
   * @param mobile the mobile
   * @return the user entity
   */
  UserEntity findByMobile(String mobile);

  /**
   * Find by user name user entity.
   *
   * @param userName the user name
   * @return the user entity
   */
  UserEntity findByUserName(String userName);

  /**
   * Find by tenant id and user name optional.
   *
   * @param tenantId the tenant id
   * @param userName the user name
   * @return the optional
   */
  Optional<UserEntity> findByTenantIdAndUserName(UUID tenantId, String userName);
}
