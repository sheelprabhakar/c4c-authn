// src/main/java/com/c4c/authz/converter/PolicyConverter.java
package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.rest.resource.PolicyResource;
import java.util.Objects;

/**
 * The type Policy converter.
 */
public final class PolicyConverter extends Converter<PolicyRecord, PolicyResource> {

    /**
     * The type Policy converter loader.
     */
    private static final class PolicyConverterLoader {
        /**
         * The constant INSTANCE.
         */
        private static final PolicyConverter INSTANCE = new PolicyConverter();
    }

    /**
     * Instantiates a new Policy converter.
     */
    private PolicyConverter() {
        super(PolicyConverter::convertToEntity, PolicyConverter::convertToResource);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PolicyConverter getInstance() {
        return PolicyConverterLoader.INSTANCE;
    }

    /**
     * Convert to entity policy record.
     *
     * @param resource the resource
     * @return the policy record
     */
    private static PolicyRecord convertToEntity(final PolicyResource resource) {
        if (Objects.isNull(resource)) {
            return null;
        }
        return new PolicyRecord(resource.getName(), resource.getPath(), resource.getVerbs());
    }

    /**
     * Convert to resource policy resource.
     *
     * @param entity the entity
     * @return the policy resource
     */
    private static PolicyResource convertToResource(final PolicyRecord entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return PolicyResource.builder()
                .name(entity.name())
                .path(entity.path())
                .verbs(entity.verbs())
                .build();
    }
}
