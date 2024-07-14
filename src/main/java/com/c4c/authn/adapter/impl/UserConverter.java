package com.c4c.authn.adapter.impl;

import com.c4c.authn.core.entity.UserEntity;
import com.c4c.authn.rest.resource.UserResource;

/**
 * The type User converter.
 */
public final class UserConverter {
  /**
   * Instantiates a new User converter.
   */
  private UserConverter() {

  }

  /**
   * From user entity user resource.
   *
   * @param entity the entity
   * @return the user resource
   */
  public static UserResource fromUserEntity(final UserEntity entity) {
    UserResource resource = new UserResource();
    resource.setId(entity.getId());
    resource.setEmail(entity.getEmail());
    resource.setIntro(entity.getIntro());
    resource.setMobile(entity.getMobile());
    resource.setProfile(entity.getProfile());
    resource.setLastLogin(entity.getLastLogin());

    resource.setLastName(entity.getLastName());
    resource.setMiddleName(entity.getMiddleName());
    resource.setPasswordHash("");
    resource.setFirstName(entity.getFirstName());
    resource.setLocked(entity.isLocked());

    resource.setDeleted(entity.isDeleted());
    resource.setCreatedAt(entity.getCreatedAt());
    resource.setUpdatedAt(entity.getUpdatedAt());
    resource.setCreatedBy(entity.getCreatedBy());
    resource.setUpdatedBy(entity.getUpdatedBy());
    return resource;
  }

  /**
   * From user resource user entity.
   *
   * @param resource the resource
   * @return the user entity
   */
  public static UserEntity fromUserResource(final UserResource resource) {
    UserEntity entity = new UserEntity();
    entity.setId(resource.getId());
    entity.setEmail(resource.getEmail());
    entity.setIntro(resource.getIntro());
    entity.setMobile(resource.getMobile());
    entity.setProfile(resource.getProfile());
    entity.setLastLogin(resource.getLastLogin());

    entity.setLastName(resource.getLastName());
    entity.setMiddleName(resource.getMiddleName());
    entity.setPasswordHash("");
    entity.setFirstName(resource.getFirstName());
    entity.setLocked(resource.isLocked());

    entity.setDeleted(resource.isDeleted());
    entity.setCreatedAt(resource.getCreatedAt());
    entity.setUpdatedAt(resource.getUpdatedAt());
    entity.setCreatedBy(resource.getCreatedBy());
    entity.setUpdatedBy(resource.getUpdatedBy());
    return entity;
  }
}
