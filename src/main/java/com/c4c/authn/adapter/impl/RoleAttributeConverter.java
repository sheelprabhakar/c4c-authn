package com.c4c.authn.adapter.impl;

import com.c4c.authn.adapter.api.Converter;
import com.c4c.authn.core.entity.RoleAttributeEntity;
import com.c4c.authn.rest.resource.RoleAttributeResource;

import java.util.Objects;


/**
 * The type Role attribute converter.
 */
public final class RoleAttributeConverter extends Converter<RoleAttributeEntity, RoleAttributeResource> {

    /**
     * The type Role attribute converter loader.
     */
    private static final class RoleAttributeConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final RoleAttributeConverter INSTANCE = new RoleAttributeConverter();
    }

    /**
     * Instantiates a new Role attribute converter.
     */
    public RoleAttributeConverter() {
        super(RoleAttributeConverter::convertToEntity, RoleAttributeConverter::convertToResource);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RoleAttributeConverter getInstance() {
        return RoleAttributeConverterLoader.INSTANCE;
    }

    /**
     * Convert to entity role attribute entity.
     *
     * @param res the res
     * @return the role attribute entity
     */
    private static RoleAttributeEntity convertToEntity(final RoleAttributeResource res) {
        if (Objects.isNull(res)) {
            return null;
        }
        return RoleAttributeEntity.builder().roleId(res.getRoleId()).attributeId(res.getAttributeId())
                .createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy())
                .updatedAt(res.getUpdatedAt()).updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

    /**
     * Convert to resource role attribute resource.
     *
     * @param entity the entity
     * @return the role attribute resource
     */
    private static RoleAttributeResource convertToResource(final RoleAttributeEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        AttributeConverter attributeConverter = AttributeConverter.getInstance();
        RoleConverter roleConverter = RoleConverter.getInstance();
        return RoleAttributeResource.builder().roleId(entity.getRoleId()).attributeId(entity.getAttributeId())
                .createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt()).updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted())
                .attributeResource(attributeConverter.covertFromEntity(entity.getAttributeEntity()))
                .roleResource(roleConverter.covertFromEntity(entity.getRoleEntity()))
                .build();
    }
}
