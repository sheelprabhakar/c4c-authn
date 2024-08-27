// src/test/java/com/c4c/authz/core/service/impl/ClientServiceImplTest.java
package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.repository.ClientRepository;
import com.c4c.authz.core.service.api.SystemTenantService;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SystemTenantService systemTenantService;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientEntity clientEntity;
    private UUID tenantId;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        tenantId = UUID.randomUUID();
        clientId = UUID.randomUUID();
        clientEntity = new ClientEntity();
        clientEntity.setTenantId(tenantId);
        clientEntity.setClientId(clientId.toString());
    }

    @Test
    void testCreate() {
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        ClientEntity createdClient = clientService.create(clientEntity);
        assertEquals(clientEntity, createdClient);
        verify(clientRepository, times(1)).save(clientEntity);
    }

    @Test
    void testUpdate() {
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        ClientEntity updatedClient = clientService.update(clientEntity);
        assertEquals(clientEntity, updatedClient);
        verify(clientRepository, times(1)).save(clientEntity);
    }

    @Test
    void testFindByTenantIdAndName() {
        when(clientRepository.findByTenantIdAndName(any(UUID.class), any(String.class))).thenReturn(Optional.of(clientEntity));
        ClientEntity foundClient = clientService.findByTenantIdAndName(tenantId, "Test Client");
        assertEquals(clientEntity, foundClient);
    }

    @Test
    void testFindByTenantIdAndClientId() {
        when(clientRepository.findByTenantIdAndClientId(any(UUID.class), any(String.class))).thenReturn(Optional.of(clientEntity));
        ClientEntity foundClient = clientService.findByTenantIdAndClientId(tenantId, clientId.toString());
        assertEquals(clientEntity, foundClient);
    }

    @Test
    void testDelete() {
        doNothing().when(clientRepository).delete(any(ClientEntity.class));
        clientService.delete(clientEntity);
        verify(clientRepository, times(1)).delete(clientEntity);
    }

    @Test
    void testFindById() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(clientEntity));
        ClientEntity foundClient = clientService.findById(clientId);
        assertEquals(clientEntity, foundClient);
    }

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

    @Test
    void testDeleteById() {
        doNothing().when(clientRepository).deleteById(any(UUID.class));
        clientService.deleteById(clientId);
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void testDeleteAllById() {
        doNothing().when(clientRepository).deleteAllById(anyList());
        List<UUID> ids = Collections.singletonList(clientId);
        clientService.deleteAllById(ids);
        verify(clientRepository, times(1)).deleteAllById(ids);
    }
}