package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.entity.UserRoleEntity;
import com.c4c.authz.rest.resource.user.UserRoleResource;
import java.util.Objects;

/**
 * The type User role converter.
 */
public final class UserRoleConverter extends Converter<UserRoleEntity, UserRoleResource> {

  /**
   * The type User role converter loader.
   */
  private static final class UserRoleConverterLoader {
    /**
     * The constant INSTANCE.
     */
    private static final UserRoleConverter INSTANCE = new UserRoleConverter();
    }

  /**
   * Instantiates a new User role converter.
   */
  public UserRoleConverter() {
        super(UserRoleConverter::convertToEntity, UserRoleConverter::convertToResource);
    }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static UserRoleConverter getInstance() {
        return UserRoleConverterLoader.INSTANCE;
    }

  /**
   * Convert to entity user role entity.
   *
   * @param res the res
   * @return the user role entity
   */
  private static UserRoleEntity convertToEntity(final UserRoleResource res) {
        if (Objects.isNull(res)) {
            return null;
        }
        return UserRoleEntity.builder().roleId(res.getRoleId()).userId(res.getUserId())
                .createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy())
                .updatedAt(res.getUpdatedAt()).updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

  /**
   * Convert to resource user role resource.
   *
   * @param entity the entity
   * @return the user role resource
   */
  private static UserRoleResource convertToResource(final UserRoleEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        UserConverter userConverter = UserConverter.getInstance();
        RoleConverter roleConverter = RoleConverter.getInstance();
        return UserRoleResource.builder().roleId(entity.getRoleId()).userId(entity.getUserId())
                .createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt()).updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted())
                .userResource(userConverter.covertFromEntity(entity.getUserEntity()))
                .roleResource(roleConverter.covertFromEntity(entity.getRoleEntity()))
                .build();
    }
}
