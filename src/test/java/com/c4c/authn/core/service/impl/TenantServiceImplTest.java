package com.c4c.authn.core.service.impl;

import com.c4c.authn.core.entity.TenantEntity;
import com.c4c.authn.core.entity.TenantUserEntity;
import com.c4c.authn.core.entity.UserEntity;
import com.c4c.authn.core.repository.TenantRepository;
import com.c4c.authn.core.service.api.TenantUserService;
import com.c4c.authn.core.service.api.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TenantServiceImplTest {

    @InjectMocks
    TenantServiceImpl tenantService;
    @Mock
    TenantRepository tenantRepository;

    @Mock
    UserService userService;

    @Mock
    TenantUserService tenantUserService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Test Create New tenant with existing user")
    void createTenantUserExists() {
        TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
        tenantEntity.setActive(false);
        UserEntity userEntity = Instancio.create(UserEntity.class);

        when(this.userService.findByEmail(anyString())).thenReturn(userEntity);
        when(this.tenantRepository.save(any(TenantEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        TenantEntity tenantEntity1 = this.tenantService.create(tenantEntity);
        assertEquals(true, tenantEntity1.isActive());
    }

    @Test
    @DisplayName("Test Create New tenant without existing user")
    void createTenantUserNotExists() {
        TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
        tenantEntity.setActive(true);

        when(this.userService.findByEmail(anyString())).thenReturn(null);
        when(this.userService.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.tenantUserService.save(any(UUID.class), any(UUID.class))).thenReturn(mock(TenantUserEntity.class));
        when(this.tenantRepository.save(any(TenantEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        TenantEntity tenantEntity1 = this.tenantService.create(tenantEntity);
        assertEquals(true, tenantEntity1.isActive());
    }

    @Test
    @DisplayName("Test update tenant with existing user")
    void updateUserExists() {
        TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
        tenantEntity.setActive(false);
        UserEntity userEntity = Instancio.create(UserEntity.class);

        when(this.userService.findByEmail(anyString())).thenReturn(userEntity);
        when(this.tenantRepository.save(any(TenantEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        TenantEntity tenantEntity1 = this.tenantService.create(tenantEntity);
        assertEquals(true, tenantEntity1.isActive());
    }

    @Test
    @DisplayName("Test Create New tenant without existing user")
    void updateTenantUserNotExists() {
        TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
        tenantEntity.setActive(true);

        when(this.userService.findByEmail(anyString())).thenReturn(null);
        when(this.userService.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.tenantUserService.save(any(UUID.class), any(UUID.class))).thenReturn(mock(TenantUserEntity.class));
        when(this.tenantRepository.save(any(TenantEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        TenantEntity tenantEntity1 = this.tenantService.create(tenantEntity);
        assertEquals(true, tenantEntity1.isActive());
    }

    @Test
    @DisplayName("Test Tenant read by ID")
    void read() {
        TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
        when(this.tenantRepository.findById(any(UUID.class))).thenReturn(Optional.of(tenantEntity));
        tenantEntity = this.tenantService.read(UUID.randomUUID());
        assertNotNull(tenantEntity);

        when(this.tenantRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        tenantEntity = this.tenantService.read(UUID.randomUUID());
        assertNull(tenantEntity);
    }

    @Test
    @DisplayName("Test read all tenants")
    void readAll() {
        List<TenantEntity> tenantEntities = Instancio.ofList(TenantEntity.class).size(5).create();
        when(this.tenantRepository.findAll()).thenReturn(tenantEntities);
        List<TenantEntity> tenantEntities1 = this.tenantService.readAll();
        assertEquals(tenantEntities, tenantEntities1);
    }
}