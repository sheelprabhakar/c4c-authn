package com.c4c.authn.adapter.impl;

import com.c4c.authn.adapter.api.Converter;
import com.c4c.authn.core.entity.RestResourceEntity;
import com.c4c.authn.rest.resource.RestResource;
import org.springframework.stereotype.Component;

/**
 * The type Token log converter.
 */
@Component("restResourceConverter")
public final class RestResourceConverter extends Converter<RestResourceEntity, RestResource> {

    /**
     * Instantiates a new Token log converter.
     */
    public RestResourceConverter() {
        super(RestResourceConverter::convertToEntity, RestResourceConverter::convertToResource);
    }

    private static RestResourceEntity convertToEntity(final RestResource res) {
        return RestResourceEntity.builder().id(res.getId()).path(res.getPath()).name(res.getName())
                .createdAt(res.getCreatedAt()).createdBy(res.getCreatedBy()).updatedAt(res.getUpdatedAt())
                .updatedBy(res.getUpdatedBy()).isDeleted(res.isDeleted()).build();
    }

    private static RestResource convertToResource(final RestResourceEntity entity) {
        return RestResource.builder().id(entity.getId()).path(entity.getPath()).name(entity.getName())
                .createdAt(entity.getCreatedAt()).createdBy(entity.getCreatedBy()).updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted()).build();
    }
}
