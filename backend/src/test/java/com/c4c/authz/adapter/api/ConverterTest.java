package com.c4c.authz.adapter.api;

import com.c4c.authz.adapter.impl.RestAclConverter;
import com.c4c.authz.adapter.impl.ClientConverter;
import com.c4c.authz.adapter.impl.RoleConverter;
import com.c4c.authz.adapter.impl.TenantConverter;
import com.c4c.authz.core.entity.RestAclEntity;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.rest.resource.RestAclResource;
import com.c4c.authz.rest.resource.ClientResource;
import com.c4c.authz.rest.resource.RoleResource;
import com.c4c.authz.rest.resource.TenantResource;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * The type Converter test.
 */
class ConverterTest {
  /**
   * The type Rest acl converter test.
   */
  @Nested
  class RestAclConverterTest {
    /**
     * The Rest acl converter.
     */
    private final RestAclConverter restAclConverter = new RestAclConverter();

    /**
     * Convert from resource.
     */
    @Test
    @DisplayName("Test convert from resource to entity")
    void convertFromResource() {
      RestAclResource restAclResource = Instancio.of(RestAclResource.class).create();
      RestAclResource restAclResource1 = this.restAclConverter.covertFromEntity(
          this.restAclConverter.convertFromResource(restAclResource));
      Assertions.assertEquals(restAclResource, restAclResource1);
    }

    /**
     * Covert from entity.
     */
    @Test
    @DisplayName("Test convert from entity to resource")
    @Disabled
    void covertFromEntity() {
      RestAclEntity restAclEntity = Instancio.of(RestAclEntity.class).create();
      RestAclEntity restAclEntity1 = this.restAclConverter.convertFromResource(
          this.restAclConverter.covertFromEntity(restAclEntity));
      Assertions.assertEquals(restAclEntity, restAclEntity1);
    }

    /**
     * Create from resources.
     */
    @Test
    @DisplayName("Test convert from resource list to entity list")
    void createFromResources() {
      List<RestAclResource> restAclResources =
          Instancio.ofList(RestAclResource.class).size(5).create();
      List<RestAclResource> restAclResources1 = this.restAclConverter.createFromEntities(
          this.restAclConverter.createFromResources(restAclResources));
      Assertions.assertTrue(restAclResources.size() == restAclResources1.size() &&
          restAclResources.containsAll(restAclResources1) &&
          restAclResources1.containsAll(restAclResources));
    }

    /**
     * Create from entities.
     */
    @Test
    @DisplayName("Test convert from entity list to resource list")
    @Disabled
    void createFromEntities() {
      List<RestAclEntity> tokenLogEntities =
          Instancio.ofList(RestAclEntity.class).size(5).create();
      List<RestAclEntity> tokenLogEntities1 = this.restAclConverter.createFromResources(
          this.restAclConverter.createFromEntities(tokenLogEntities));
      Assertions.assertTrue(tokenLogEntities.size() == tokenLogEntities1.size() &&
          tokenLogEntities.containsAll(tokenLogEntities1) &&
          tokenLogEntities1.containsAll(tokenLogEntities));
    }
  }

  /**
   * The type Tenant converter test.
   */
  @Nested
  class TenantConverterTest {
    /**
     * The Tenant converter.
     */
    private final TenantConverter tenantConverter = new TenantConverter();

    /**
     * Convert from resource.
     */
    @Test
    @DisplayName("Test convert from resource to entity")
    void convertFromResource() {
      TenantResource resource = Instancio.of(TenantResource.class).create();
      TenantResource resource1 = this.tenantConverter.covertFromEntity(
              this.tenantConverter.convertFromResource(resource));
      resource1.setCityId(resource.getCityId());
      Assertions.assertEquals(resource, resource1);
    }

    /**
     * Covert from entity.
     */
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

    /**
     * Create from resources.
     */
    @Test
    @DisplayName("Test convert from resource list to entity list")
    void createFromResources() {
      List<TenantResource> resources =
              Instancio.ofList(TenantResource.class).size(5).create();
      List<TenantResource> resources1 = this.tenantConverter.createFromEntities(
              this.tenantConverter.createFromResources(resources));
        Assertions.assertEquals(resources.size(), resources1.size());
    }

    /**
     * Create from entities.
     */
    @Test
    @DisplayName("Test convert from entity list to resource list")
    void createFromEntities() {
      List<TenantEntity> entities =
              Instancio.ofList(TenantEntity.class).size(5).create();
      List<TenantEntity> entities1 = this.tenantConverter.createFromResources(
              this.tenantConverter.createFromEntities(entities));

        Assertions.assertEquals(entities.size(), entities1.size());
    }
  }


  /**
   * The type Role converter test.
   */
  @Nested
  class RoleConverterTest {
    /**
     * The Role converter.
     */
    private final RoleConverter roleConverter = new RoleConverter();

    /**
     * Convert from resource.
     */
    @Test
    @DisplayName("Test convert from resource to entity")
    void convertFromResource() {
      RoleResource resource = Instancio.of(RoleResource.class).create();
      RoleResource resource1 = this.roleConverter.covertFromEntity(
              this.roleConverter.convertFromResource(resource));
      Assertions.assertEquals(resource, resource1);
    }

    /**
     * Covert from entity.
     */
    @Test
    @DisplayName("Test convert from entity to resource")
    void covertFromEntity() {
      RoleEntity entity = Instancio.of(RoleEntity.class).create();
      RoleEntity entity1 = this.roleConverter.convertFromResource(
              this.roleConverter.covertFromEntity(entity));
      entity1.setUserRoleEntities(entity.getUserRoleEntities());
      Assertions.assertEquals(entity, entity1);
    }

    /**
     * Create from resources.
     */
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

    /**
     * Create from entities.
     */
    @Test
    @DisplayName("Test convert from entity list to resource list")
    void createFromEntities() {
      List<RoleEntity> entities =
              Instancio.ofList(RoleEntity.class).size(5).create();
      List<RoleEntity> entities1 = this.roleConverter.createFromResources(
              this.roleConverter.createFromEntities(entities));
        Assertions.assertEquals(entities.size(), entities1.size());
    }
  }

  /**
   * The type Client converter test.
   */
  @Nested
  class ClientConverterTest {
    /**
     * The Client converter.
     */
    private final ClientConverter clientConverter = ClientConverter.getInstance();

    /**
     * Convert from resource.
     */
    @Test
    @DisplayName("Test convert from resource to entity")
    void convertFromResource() {
      ClientResource resource = Instancio.of(ClientResource.class).create();
      ClientResource resource1 = this.clientConverter.covertFromEntity(
          this.clientConverter.convertFromResource(resource));
      Assertions.assertEquals(resource, resource1);
    }

    /**
     * Covert from entity.
     */
    @Test
    @DisplayName("Test convert from entity to resource")
    void covertFromEntity() {
      ClientEntity entity = Instancio.of(ClientEntity.class).create();
      ClientEntity entity1 = this.clientConverter.convertFromResource(
          this.clientConverter.covertFromEntity(entity));
      Assertions.assertEquals(entity, entity1);
    }

    /**
     * Create from resources.
     */
    @Test
    @DisplayName("Test convert from resource list to entity list")
    void createFromResources() {
      List<ClientResource> resources =
          Instancio.ofList(ClientResource.class).size(5).create();
      List<ClientResource> resources1 = this.clientConverter.createFromEntities(
          this.clientConverter.createFromResources(resources));
      Assertions.assertTrue(resources.size() == resources1.size() &&
          resources.containsAll(resources1) &&
          resources1.containsAll(resources));
    }

    /**
     * Create from entities.
     */
    @Test
    @DisplayName("Test convert from entity list to resource list")
    void createFromEntities() {
      List<ClientEntity> entities =
          Instancio.ofList(ClientEntity.class).size(5).create();
      List<ClientEntity> entities1 = this.clientConverter.createFromResources(
          this.clientConverter.createFromEntities(entities));
      Assertions.assertTrue(entities.size() == entities1.size() &&
          entities.containsAll(entities1) &&
          entities1.containsAll(entities));
    }
  }
}