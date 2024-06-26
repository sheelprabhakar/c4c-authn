package com.c4c.authn.utils;

import com.c4c.authn.core.entity.UserEntity;
import java.util.Calendar;
import java.util.UUID;

/**
 * The type User entity helper.
 */
public class UserEntityHelper {
  /**
   * The constant MOBILE.
   */
  public static final String MOBILE = "9898989898";

  /**
   * Instantiates a new User entity helper.
   */
  private UserEntityHelper() {
  }

  /**
   * Gets new.
   *
   * @param id the id
   * @return the new
   */
  public static UserEntity getNew(final UUID id) {
    UserEntity entity = new UserEntity();
    entity.setId(id);
    entity.setEmail("ssp@c4c.com");
    entity.setIntro("");
    entity.setMobile(MOBILE);
    entity.setProfile("");
    entity.setLastLogin(null);

    entity.setLastName("prabhakar");
    entity.setMiddleName("s");
    entity.setPasswordHash("");
    entity.setFirstName("sheel");

    entity.setCreatedAt(Calendar.getInstance());
    entity.setUpdatedAt(Calendar.getInstance());
    entity.setCreatedBy("ssp");
    entity.setUpdatedBy("ssp_u");

    return entity;
  }
}
