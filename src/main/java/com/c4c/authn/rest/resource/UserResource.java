package com.c4c.authn.rest.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Calendar;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResource extends CommonResourceAttributes {
  /**
   * The Id.
   */
  private UUID id;

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
   * The Username.
   */
  @NotNull
  @Size(max = 45)
  private String userName;

}
