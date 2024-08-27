// src/main/java/com/c4c/authz/converter/PolicyConverter.java
package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.Converter;
import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.rest.resource.PolicyResource;

import java.util.Objects;

public final class PolicyConverter extends Converter<PolicyRecord, PolicyResource> {

    private static final class PolicyConverterLoader {
        private static final PolicyConverter INSTANCE = new PolicyConverter();
    }

    private PolicyConverter() {
        super(PolicyConverter::convertToEntity, PolicyConverter::convertToResource);
    }

    public static PolicyConverter getInstance() {
        return PolicyConverterLoader.INSTANCE;
    }

    private static PolicyRecord convertToEntity(final PolicyResource resource) {
        if (Objects.isNull(resource)) {
            return null;
        }
        return new PolicyRecord(resource.getName(), resource.getPath(), resource.getVerbs());
    }

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