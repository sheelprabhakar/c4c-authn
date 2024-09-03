package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.entity.RoleRestAclEntity;
import com.c4c.authz.rest.resource.RoleRestAclResource;
import java.util.Objects;


/**
 * The type Role rest acl converter.
 */
public final class RoleRestAclConverter extends Converter<RoleRestAclEntity, RoleRestAclResource> {

    /**
     * The type Role rest acl converter converter loader.
     */
    private static final class RoleRestAclConverterConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final RoleRestAclConverter INSTANCE = new RoleRestAclConverter();
    }

    /**
     * Instantiates a new Role rest acl converter.
     */
    public RoleRestAclConverter() {
        super(RoleRestAclConverter::convertToEntity, RoleRestAclConverter::convertToResource);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RoleRestAclConverter getInstance() {
        return RoleRestAclConverterConverterLoader.INSTANCE;
    }

    /**
     * Convert to entity role rest acl entity.
     *
     * @param res the res
     * @return the role rest acl entity
     */
    private static RoleRestAclEntity convertToEntity(final RoleRestAclResource res) {
        if (Objects.isNull(res)) {
            return null;
        }
        return RoleRestAclEntity.builder().roleId(res.getRoleId()).restAclId(res.getRestAclId())
                .canCreate(res.isCanCreate()).canDelete(res.isCanDelete()).canRead(res.isCanRead())
                .canUpdate(res.isCanUpdate())
                .createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy())
                .updatedAt(res.getUpdatedAt()).updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

    /**
     * Convert to resource role rest acl resource.
     *
     * @param entity the entity
     * @return the role rest acl resource
     */
    private static RoleRestAclResource convertToResource(final RoleRestAclEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        RestAclConverter restAclConverter = RestAclConverter.getInstance();
        RoleConverter roleConverter = RoleConverter.getInstance();
        return RoleRestAclResource.builder().roleId(entity.getRoleId()).restAclId(entity.getRestAclId())
                .canCreate(entity.isCanCreate()).canDelete(entity.isCanDelete()).canRead(entity.isCanRead())
                .canUpdate(entity.isCanUpdate())
                .createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt()).updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted())
                .restAclResource(restAclConverter.covertFromEntity(entity.getRestAclEntity()))
                .roleResource(roleConverter.covertFromEntity(entity.getRoleEntity()))
                .build();
    }
}
