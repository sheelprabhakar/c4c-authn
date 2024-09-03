package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.entity.RestAclEntity;
import com.c4c.authz.rest.resource.RestAclResource;
import java.util.Objects;

/**
 * The type Rest acl converter.
 */
public final class RestAclConverter extends Converter<RestAclEntity, RestAclResource> {

    /**
     * The type Rest acl resource converter loader.
     */
    private static final class RestAclResourceConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final RestAclConverter INSTANCE = new RestAclConverter();
    }

    /**
     * Instantiates a new Rest acl converter.
     */
    public RestAclConverter() {
        super(RestAclConverter::convertToEntity, RestAclConverter::convertToResource);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RestAclConverter getInstance() {
        return RestAclResourceConverterLoader.INSTANCE;
    }

    /**
     * Convert to entity rest acl entity.
     *
     * @param res the res
     * @return the rest acl entity
     */
    private static RestAclEntity convertToEntity(final RestAclResource res) {
        if (Objects.isNull(res)) {
            return null;
        }
        return RestAclEntity.builder().id(res.getId()).tenantId(res.getTenantId()).path(res.getPath())
                .name(res.getName()).createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy())
                .updatedAt(res.getUpdatedAt()).updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

    /**
     * Convert to resource rest acl resource.
     *
     * @param entity the entity
     * @return the rest acl resource
     */
    private static RestAclResource convertToResource(final RestAclEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return RestAclResource.builder().id(entity.getId()).tenantId(entity.getTenantId()).path(entity.getPath())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt()).updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted())
                .build();
    }
}
