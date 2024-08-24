package com.c4c.authz.rest.resource.user;

import com.c4c.authz.rest.resource.CommonResourceAttributes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

/**
 * The type User resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class UserResource extends CommonResourceAttributes implements Serializable {
  /**
   * The Id.
   */
  private UUID id;

  /**
   * The Tenant id.
   */
  private UUID tenantId;

  /**
   * The First name.
   */
  private String firstName;

  /**
   * The Middle name.
   */
  private String middleName;

  /**
   * The Last name.
   */
  private String lastName;

  /**
   * The Mobile.
   */
  @NotBlank
  private String mobile;

  /**
   * The Email.
   */
  @NotBlank
  private String email;

  /**
   * The Password hash.
   */
  private String passwordHash;

  /**
   * The Last login.
   */
  private Calendar lastLogin;

  /**
   * The Intro.
   */
  private String intro;
  /**
   * The Profile.
   */
  private String profile;

  /**
   * The Is locked.
   */
  private boolean isLocked;

  /**
   * The Is deleted.
   */
  private boolean isDeleted;

  /**
   * The User name.
   */
  @NotNull
  @Size(max = 45)
  private String userName;

}
