package com.c4c.authn.adapter.impl;

import com.c4c.authn.adapter.api.Converter;
import com.c4c.authn.core.entity.RoleEntity;
import com.c4c.authn.rest.resource.RoleResource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * The type Token log converter.
 */
@Component("roleConverter")
public final class RoleConverter extends Converter<RoleEntity, RoleResource> {

    /**
     * Instantiates a new Token log converter.
     */
    public RoleConverter() {
        super(RoleConverter::convertToEntity, RoleConverter::convertToResource);
    }

    private static RoleEntity convertToEntity(final RoleResource res) {
        if (Objects.isNull(res)) {
            return null;
        }
        return RoleEntity.builder().id(res.getId()).tenantId(res.getTenantId()).name(res.getName())
                .createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy())
                .updatedAt(res.getUpdatedAt()).updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

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
