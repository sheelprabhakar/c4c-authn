package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.rest.resource.user.UserResource;
import java.util.Objects;

/**
 * The type User converter.
 */
public final class UserConverter extends Converter<UserEntity, UserResource> {

    /**
     * The type User converter loader.
     */
    private static final class UserConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final UserConverter INSTANCE = new UserConverter();
    }

    /**
     * Instantiates a new User converter.
     */
    private UserConverter() {
        super(UserConverter::fromUserResource, UserConverter::fromUserEntity);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserConverter getInstance() {
        return UserConverterLoader.INSTANCE;
    }

    /**
     * From user entity user resource.
     *
     * @param entity the entity
     * @return the user resource
     */
    private static UserResource fromUserEntity(final UserEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        UserResource resource = new UserResource();
        resource.setId(entity.getId());
        resource.setUserName(entity.getUserName());
        resource.setTenantId(entity.getTenantId());
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
    private static UserEntity fromUserResource(final UserResource resource) {
        if (Objects.isNull(resource)) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(resource.getId());
        entity.setTenantId(resource.getTenantId());
        entity.setUserName(resource.getUserName());
        entity.setEmail(resource.getEmail());
        entity.setIntro(resource.getIntro());
        entity.setMobile(resource.getMobile());
        entity.setProfile(resource.getProfile());
        entity.setLastLogin(resource.getLastLogin());

        entity.setLastName(resource.getLastName());
        entity.setMiddleName(resource.getMiddleName());
        entity.setPasswordHash(resource.getPasswordHash());
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
