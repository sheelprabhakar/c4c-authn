package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.rest.resource.TenantResource;
import java.util.Objects;

/**
 * The type Tenant converter.
 */
public final class TenantConverter extends Converter<TenantEntity, TenantResource> {

    /**
     * The type Tenant converter loader.
     */
    private static final class TenantConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final TenantConverter INSTANCE = new TenantConverter();
    }

    /**
     * Instantiates a new Tenant converter.
     */
    public TenantConverter() {
        super(TenantConverter::convertToEntity, TenantConverter::convertToResource);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TenantConverter getInstance() {
        return TenantConverterLoader.INSTANCE;
    }

    /**
     * Convert to entity tenant entity.
     *
     * @param res the res
     * @return the tenant entity
     */
    private static TenantEntity convertToEntity(final TenantResource res) {
        if (Objects.isNull(res)) {
            return null;
        }
        return TenantEntity.builder().id(res.getId()).name(res.getName()).shortName(res.getShortName())
                .active(res.isActive()).area(res.getArea()).pictureUrl(res.getPictureUrl()).email(res.getEmail())
                .landmark(res.getLandmark()).latitude(res.getLatitude()).longitude(res.getLongitude())
                .mobile(res.getMobile()).pin(res.getPin()).phone(res.getPhone()).address(res.getAddress())
                .createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy()).updatedAt(res.getUpdatedAt())
                .updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

    /**
     * Convert to resource tenant resource.
     *
     * @param entity the entity
     * @return the tenant resource
     */
    private static TenantResource convertToResource(final TenantEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return TenantResource.builder().id(entity.getId()).name(entity.getName()).shortName(entity.getShortName())
                .active(entity.isActive()).area(entity.getArea()).pictureUrl(entity.getPictureUrl())
                .email(entity.getEmail()).landmark(entity.getLandmark()).latitude(entity.getLatitude())
                .longitude(entity.getLongitude()).mobile(entity.getMobile()).pin(entity.getPin())
                .cityId(entity.getCity() != null ? entity.getCity().getId() : -1).phone(entity.getPhone())
                .address(entity.getAddress())
                .createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy()).updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted()).build();
    }
}
