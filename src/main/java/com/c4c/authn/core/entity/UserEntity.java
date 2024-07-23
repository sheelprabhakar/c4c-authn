package com.c4c.authn.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * The type User entity.
 */
@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends CommonEntityAttributes implements Serializable {

  /**
   * The constant NAME_MAX.
   */
  private static final int NAME_MAX = 50;
  /**
   * The constant HASH_MAX.
   */
  private static final int HASH_MAX = 64;
  /**
   * The constant MOBILE_MAX.
   */
  private static final int MOBILE_MAX = 15;
  /**
   * The Id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID id;

  /**
   * The Tenant id.
   */
  @Column(name = "tenant_id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID tenantId;

  /**
   * The First name.
   */
  @Column(name = "first_name", length = NAME_MAX)
  private String firstName;

  /**
   * The Middle name.
   */
  @Column(name = "middle_name", length = NAME_MAX)
  private String middleName;

  /**
   * The Last name.
   */
  @Column(name = "last_name", length = NAME_MAX)
  private String lastName;

  /**
   * The Mobile.
   */
  @Column(name = "mobile", length = MOBILE_MAX)
  private String mobile;

  /**
   * The User name.
   */
  @Column(name = "user_name", length = NAME_MAX)
  private String userName;

  /**
   * The Email.
   */
  @Column(name = "email", length = NAME_MAX)
  private String email;

  /**
   * The Password hash.
   */
  @Column(name = "password_hash", length = HASH_MAX, nullable = false)
  private String passwordHash;

  /**
   * The Otp.
   */
  @Column(name = "otp", length = HASH_MAX, nullable = false)
  private String otp;

  /**
   * The Otp at.
   */
  @Column(name = "otp_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar otpAt;

  /**
   * The Last login.
   */
  @Column(name = "last_login")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar lastLogin;

  /**
   * The Intro.
   */
  @Column(name = "intro")
  private String intro;

  /**
   * The Profile.
   */
  @Column(name = "profile")
  private String profile;

  /**
   * The Is locked.
   */
  @Column(name = "is_locked", nullable = false)
  private boolean isLocked;

  /**
   * The Roles.
   */
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private List<UserRoleEntity> roles;

  /**
   * The Is deleted.
   */
  @NotNull
  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted;

}
