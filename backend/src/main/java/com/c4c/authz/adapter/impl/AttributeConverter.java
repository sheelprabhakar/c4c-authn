package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.entity.AttributeEntity;
import com.c4c.authz.rest.resource.AttributeResource;

import java.util.Objects;

/**
 * The type Attribute converter.
 */
public final class AttributeConverter extends Converter<AttributeEntity, AttributeResource> {

    /**
     * The type Attribute resource converter loader.
     */
    private static final class AttributeResourceConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final AttributeConverter INSTANCE = new AttributeConverter();
    }

    /**
     * Instantiates a new Attribute converter.
     */
    public AttributeConverter() {
        super(AttributeConverter::convertToEntity, AttributeConverter::convertToResource);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AttributeConverter getInstance() {
        return AttributeResourceConverterLoader.INSTANCE;
    }

    /**
     * Convert to entity attribute entity.
     *
     * @param res the res
     * @return the attribute entity
     */
    private static AttributeEntity convertToEntity(final AttributeResource res) {
        if (Objects.isNull(res)) {
            return null;
        }
        return AttributeEntity.builder().id(res.getId()).tenantId(res.getTenantId()).path(res.getPath())
                .name(res.getName()).createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy())
                .updatedAt(res.getUpdatedAt()).updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

    /**
     * Convert to resource attribute resource.
     *
     * @param entity the entity
     * @return the attribute resource
     */
    private static AttributeResource convertToResource(final AttributeEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return AttributeResource.builder().id(entity.getId()).tenantId(entity.getTenantId()).path(entity.getPath())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt()).updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted())
                .build();
    }
}
