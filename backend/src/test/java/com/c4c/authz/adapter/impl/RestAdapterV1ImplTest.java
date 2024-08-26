package com.c4c.authz.adapter.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.core.entity.lookup.CityEntity;
import com.c4c.authz.core.service.api.LookupService;
import com.c4c.authz.core.service.api.TenantService;
import com.c4c.authz.core.service.api.UserRoleService;
import com.c4c.authz.rest.resource.TenantResource;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestAdapterV1ImplTest {

  @Mock
  private UserRoleService userRoleService;

  @Mock
  private LookupService lookupService;

  @InjectMocks
  private RestAdapterV1Impl restAdapterV1Impl;
  @Mock
  private TenantService tenantService;

  @Mock
  private TenantConverter tenantConverter;


  @Test
  void testCreateTenant() {
    // Arrange
    TenantResource tenantResource = Instancio.create(TenantResource.class);
    TenantEntity tenantEntity = TenantConverter.getInstance().convertFromResource(tenantResource);
    when(tenantService.create(any(TenantEntity.class))).thenReturn(tenantEntity);
    //when(tenantConverter.covertFromEntity(any(TenantEntity.class))).thenReturn(expectedTenantResource);
    when(lookupService.getCityById(anyInt())).thenReturn(Instancio.create(CityEntity.class));
    // Act
    TenantResource result = restAdapterV1Impl.createTenant(tenantResource);

    // Assert
    assertNotNull(result);
    verify(tenantService).create(any(TenantEntity.class));
    assertEquals(tenantResource.getId(), result.getId());
  }

}