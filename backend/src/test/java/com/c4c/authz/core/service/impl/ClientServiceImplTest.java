// src/test/java/com/c4c/authz/core/service/impl/ClientServiceImplTest.java

package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.repository.ClientRepository;
import com.c4c.authz.core.service.api.ClientRoleService;
import com.c4c.authz.core.service.api.RoleService;
import com.c4c.authz.core.service.api.SystemTenantService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * The type Client service impl test.
 */
@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

  /**
   * The Client repository.
   */
  @Mock
  private ClientRepository clientRepository;

  /**
   * The Role service.
   */
  @Mock
  private RoleService roleService;

  /**
   * The Client role service.
   */
  @Mock
  private ClientRoleService clientRoleService;

  /**
   * The System tenant service.
   */
  @Mock
  private SystemTenantService systemTenantService;

  /**
   * The Client service.
   */
  @InjectMocks
  private ClientServiceImpl clientService;

  /**
   * The Client entity.
   */
  private ClientEntity clientEntity;
  /**
   * The Tenant id.
   */
  private UUID tenantId;
  /**
   * The Client id.
   */
  private UUID clientId;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    tenantId = UUID.randomUUID();
    clientId = UUID.randomUUID();
    clientEntity = Instancio.create(ClientEntity.class);
    clientEntity.setTenantId(tenantId);
    clientEntity.setClientId(clientId.toString());
  }

  /**
   * Test create.
   */
  @Test
  void testCreate() {
    when(roleService.findByTenantIdAndName(any(UUID.class), anyString())).thenReturn(
        Instancio.create(RoleEntity.class));
    when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
    ClientEntity createdClient = clientService.create(clientEntity);
    assertEquals(clientEntity, createdClient);
    verify(clientRepository, times(1)).save(clientEntity);
  }

  /**
   * Test update.
   */
  @Test
  void testUpdate() {
    when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of( clientEntity));
    when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
    ClientEntity updatedClient = clientService.update(clientEntity);
    assertEquals(clientEntity, updatedClient);
    verify(clientRepository, times(1)).save(clientEntity);
  }

  /**
   * Test find by tenant id and name.
   */
  @Test
  void testFindByTenantIdAndName() {
    when(clientRepository.findByTenantIdAndName(any(UUID.class), any(String.class))).thenReturn(
        Optional.of(clientEntity));
    ClientEntity foundClient = clientService.findByTenantIdAndName(tenantId, "Test Client");
    assertEquals(clientEntity, foundClient);
  }

  /**
   * Test find by tenant id and client id.
   */
  @Test
  void testFindByTenantIdAndClientId() {
    when(clientRepository.findByTenantIdAndClientId(any(UUID.class), any(String.class))).thenReturn(
        Optional.of(clientEntity));
    ClientEntity foundClient = clientService.findByTenantIdAndClientId(tenantId, clientId.toString());
    assertEquals(clientEntity, foundClient);
  }

  /**
   * Test delete.
   */
  @Test
  void testDelete() {
    doNothing().when(clientRepository).delete(any(ClientEntity.class));
    clientService.delete(clientEntity);
    verify(clientRepository, times(1)).delete(clientEntity);
  }

  /**
   * Test find by id.
   */
  @Test
  void testFindById() {
    when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(clientEntity));
    ClientEntity foundClient = clientService.findById(clientId);
    assertEquals(clientEntity, foundClient);
  }

  /**
   * Test find all.
   */
  @Test
  void testFindAll() {
    try (MockedStatic<CurrentUserContext> mockedStatic = mockStatic(CurrentUserContext.class)) {
      mockedStatic.when(CurrentUserContext::getCurrentTenantId).thenReturn(tenantId);
      when(systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(true);
      when(clientRepository.findAll()).thenReturn(Collections.singletonList(clientEntity));
      List<ClientEntity> clients = clientService.findAll();
      assertEquals(1, clients.size());
      assertEquals(clientEntity, clients.get(0));
    }
  }

  /**
   * Test find by pagination.
   */
  @Test
  void testFindByPagination() {
    try (MockedStatic<CurrentUserContext> mockedStatic = mockStatic(CurrentUserContext.class)) {
      mockedStatic.when(CurrentUserContext::getCurrentTenantId).thenReturn(tenantId);
      when(systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(true);
      Page<ClientEntity> page = new PageImpl<>(Collections.singletonList(clientEntity));
      when(clientRepository.findAll(any(PageRequest.class))).thenReturn(page);
      Page<ClientEntity> clients = clientService.findByPagination(0, 10);
      assertEquals(1, clients.getTotalElements());
      assertEquals(clientEntity, clients.getContent().get(0));
    }
  }

  /**
   * Test delete by id.
   */
  @Test
  void testDeleteById() {
    doNothing().when(clientRepository).deleteById(any(UUID.class));
    clientService.deleteById(clientId);
    verify(clientRepository, times(1)).deleteById(clientId);
  }

  /**
   * Test delete all by id.
   */
  @Test
  void testDeleteAllById() {
    doNothing().when(clientRepository).deleteAllById(anyList());
    List<UUID> ids = Collections.singletonList(clientId);
    clientService.deleteAllById(ids);
    verify(clientRepository, times(1)).deleteAllById(ids);
  }
}