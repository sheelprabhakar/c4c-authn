package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.rest.resource.RoleResource;
import java.util.Objects;

/**
 * The type Role converter.
 */
public final class RoleConverter extends Converter<RoleEntity, RoleResource> {

    /**
     * The type Role converter loader.
     */
    private static final class RoleConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final RoleConverter INSTANCE = new RoleConverter();
    }

    /**
     * Instantiates a new Role converter.
     */
    public RoleConverter() {
        super(RoleConverter::convertToEntity, RoleConverter::convertToResource);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RoleConverter getInstance() {
        return RoleConverterLoader.INSTANCE;
    }

    /**
     * Convert to entity role entity.
     *
     * @param res the res
     * @return the role entity
     */
    private static RoleEntity convertToEntity(final RoleResource res) {
        if (Objects.isNull(res)) {
            return null;
        }
        return RoleEntity.builder().id(res.getId()).tenantId(res.getTenantId()).name(res.getName())
                .createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy())
                .updatedAt(res.getUpdatedAt()).updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

    /**
     * Convert to resource role resource.
     *
     * @param entity the entity
     * @return the role resource
     */
    private static RoleResource convertToResource(final RoleEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return RoleResource.builder().id(entity.getId()).tenantId(entity.getTenantId()).name(entity.getName())
                .createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt()).updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted())
                .build();
    }
}
