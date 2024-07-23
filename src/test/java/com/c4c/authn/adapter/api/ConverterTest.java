package com.c4c.authn.adapter.api;

import com.c4c.authn.adapter.impl.RestResourceConverter;
import com.c4c.authn.core.entity.RestResourceEntity;
import com.c4c.authn.rest.resource.RestResource;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ConverterTest {
  @Nested
  class RestResourceConverterTest {
    private final RestResourceConverter restResourceConverter = new RestResourceConverter();
    @Test
    @DisplayName("Test convert from resource to entity")
    void convertFromResource() {
      RestResource restResource = Instancio.of(RestResource.class).create();
      RestResource restResource1 = this.restResourceConverter.covertFromEntity(
          this.restResourceConverter.convertFromResource(restResource));
      Assertions.assertEquals(restResource, restResource1);
    }

    @Test
    @DisplayName("Test convert from entity to resource")
    void covertFromEntity() {
      RestResourceEntity restResourceEntity = Instancio.of(RestResourceEntity.class).create();
      RestResourceEntity restResourceEntity1 = this.restResourceConverter.convertFromResource(
          this.restResourceConverter.covertFromEntity(restResourceEntity));
      Assertions.assertEquals(restResourceEntity, restResourceEntity1);
    }

    @Test
    @DisplayName("Test convert from resource list to entity list")
    void createFromResources() {
      List<RestResource> restResources =
          Instancio.ofList(RestResource.class).size(5).create();
      List<RestResource> restResources1 = this.restResourceConverter.createFromEntities(
          this.restResourceConverter.createFromResources(restResources));
      Assertions.assertTrue(restResources.size() == restResources1.size() &&
          restResources.containsAll(restResources1) &&
          restResources1.containsAll(restResources));
    }

    @Test
    @DisplayName("Test convert from entity list to resource list")
    void createFromEntities() {
      List<RestResourceEntity> tokenLogEntities =
          Instancio.ofList(RestResourceEntity.class).size(5).create();
      List<RestResourceEntity> tokenLogEntities1 = this.restResourceConverter.createFromResources(
          this.restResourceConverter.createFromEntities(tokenLogEntities));
      Assertions.assertTrue(tokenLogEntities.size() == tokenLogEntities1.size() &&
          tokenLogEntities.containsAll(tokenLogEntities1) &&
          tokenLogEntities1.containsAll(tokenLogEntities));
    }
  }
}