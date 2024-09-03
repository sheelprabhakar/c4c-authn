package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.rest.resource.ClientResource;
import java.util.Objects;

/**
 * The type Client converter.
 */
public final class ClientConverter extends Converter<ClientEntity, ClientResource> {

    /**
     * The type Client converter loader.
     */
    private static final class ClientConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final ClientConverter INSTANCE = new ClientConverter();
  }

    /**
     * Instantiates a new Client converter.
     */
    private ClientConverter() {
    super(ClientConverter::convertToEntity, ClientConverter::convertToResource);
  }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ClientConverter getInstance() {
    return ClientConverterLoader.INSTANCE;
  }

    /**
     * Convert to entity client entity.
     *
     * @param resource the resource
     * @return the client entity
     */
    private static ClientEntity convertToEntity(final ClientResource resource) {
    if (Objects.isNull(resource)) {
      return null;
    }
    return ClientEntity.builder()
        .id(resource.getId())
        .tenantId(resource.getTenantId())
        .clientId(resource.getClientId())
        .clientSecret(resource.getClientSecret())
        .name(resource.getName())
        .createdAt(resource.getCreatedAt()).createdBy(resource.getCreatedBy())
        .updatedAt(resource.getUpdatedAt()).updatedBy(resource.getUpdatedBy()).isDeleted(resource.isDeleted()).build();
  }

    /**
     * Convert to resource client resource.
     *
     * @param entity the entity
     * @return the client resource
     */
    private static ClientResource convertToResource(final ClientEntity entity) {
    if (Objects.isNull(entity)) {
      return null;
    }
    return ClientResource.builder()
        .id(entity.getId())
        .tenantId(entity.getTenantId())
        .clientId(entity.getClientId())
        .clientSecret(entity.getClientSecret())
        .name(entity.getName())
        .createdAt(entity.getCreatedAt())
        .createdBy(entity.getCreatedBy())
        .updatedAt(entity.getUpdatedAt()).updatedBy(entity.getUpdatedBy()).isDeleted(entity.isDeleted())
        .build();
  }
}
