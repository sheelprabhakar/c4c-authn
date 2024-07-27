package com.c4c.authn.adapter.api;

import com.c4c.authn.adapter.impl.RestResourceConverter;
import com.c4c.authn.adapter.impl.RoleConverter;
import com.c4c.authn.adapter.impl.TenantConverter;
import com.c4c.authn.core.entity.RestResourceEntity;
import com.c4c.authn.core.entity.RoleEntity;
import com.c4c.authn.core.entity.TenantEntity;
import com.c4c.authn.rest.resource.RestResource;
import com.c4c.authn.rest.resource.RoleResource;
import com.c4c.authn.rest.resource.TenantResource;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

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

  @Nested
  class TenantConverterTest {
    private final TenantConverter tenantConverter = new TenantConverter();
    @Test
    @DisplayName("Test convert from resource to entity")
    void convertFromResource() {
      TenantResource resource = Instancio.of(TenantResource.class).create();
      TenantResource resource1 = this.tenantConverter.covertFromEntity(
              this.tenantConverter.convertFromResource(resource));
      resource1.setCityId(resource.getCityId());
      Assertions.assertEquals(resource, resource1);
    }

    @Test
    @DisplayName("Test convert from entity to resource")
    void covertFromEntity() {
      TenantEntity entity = Instancio.of(TenantEntity.class).create();
      TenantEntity entity1 = this.tenantConverter.convertFromResource(
              this.tenantConverter.covertFromEntity(entity));
      // City is not handled
      entity1.setCity(entity.getCity());
      Assertions.assertEquals(entity, entity1);
    }

    @Test
    @DisplayName("Test convert from resource list to entity list")
    void createFromResources() {
      List<TenantResource> resources =
              Instancio.ofList(TenantResource.class).size(5).create();
      List<TenantResource> resources1 = this.tenantConverter.createFromEntities(
              this.tenantConverter.createFromResources(resources));
      Assertions.assertTrue(resources.size() == resources1.size());
    }

    @Test
    @DisplayName("Test convert from entity list to resource list")
    void createFromEntities() {
      List<TenantEntity> entities =
              Instancio.ofList(TenantEntity.class).size(5).create();
      List<TenantEntity> entities1 = this.tenantConverter.createFromResources(
              this.tenantConverter.createFromEntities(entities));

      Assertions.assertTrue(entities.size() == entities1.size());
    }
  }


  @Nested
  class RoleConverterTest {
    private final RoleConverter roleConverter = new RoleConverter();
    @Test
    @DisplayName("Test convert from resource to entity")
    void convertFromResource() {
      RoleResource resource = Instancio.of(RoleResource.class).create();
      RoleResource resource1 = this.roleConverter.covertFromEntity(
              this.roleConverter.convertFromResource(resource));
      Assertions.assertEquals(resource, resource1);
    }

    @Test
    @DisplayName("Test convert from entity to resource")
    void covertFromEntity() {
      RoleEntity entity = Instancio.of(RoleEntity.class).create();
      RoleEntity entity1 = this.roleConverter.convertFromResource(
              this.roleConverter.covertFromEntity(entity));
      entity1.setUserRoleEntities(entity.getUserRoleEntities());
      Assertions.assertEquals(entity, entity1);
    }

    @Test
    @DisplayName("Test convert from resource list to entity list")
    void createFromResources() {
      List<RoleResource> resources =
              Instancio.ofList(RoleResource.class).size(5).create();
      List<RoleResource> resources1 = this.roleConverter.createFromEntities(
              this.roleConverter.createFromResources(resources));
      Assertions.assertTrue(resources.size() == resources1.size() &&
              resources.containsAll(resources1) &&
              resources1.containsAll(resources));
    }

    @Test
    @DisplayName("Test convert from entity list to resource list")
    void createFromEntities() {
      List<RoleEntity> entities =
              Instancio.ofList(RoleEntity.class).size(5).create();
      List<RoleEntity> entities1 = this.roleConverter.createFromResources(
              this.roleConverter.createFromEntities(entities));
      Assertions.assertTrue(entities.size() == entities1.size());
    }
  }
}