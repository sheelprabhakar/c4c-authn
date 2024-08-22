package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.repository.TenantRepository;
import com.c4c.authz.core.service.api.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

/**
 * The type Tenant service impl test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TenantServiceImplTest {

    /**
     * The Tenant service.
     */
    @InjectMocks
    @Spy
    TenantServiceImpl tenantService;
    /**
     * The Tenant repository.
     */
    @Mock
    TenantRepository tenantRepository;
    /**
     * The User service.
     */
    @Mock
    UserService userService;

    /**
     * The System tenant entity.
     */
    @Mock
    TenantEntity systemTenantEntity;

    /**
     * Create tenant user exists.
     */
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

    /**
     * Create tenant user not exists.
     */
    @Test
    @DisplayName("Test Create New tenant without existing user")
    void createTenantUserNotExists() {
        TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
        tenantEntity.setActive(true);

        when(this.userService.findByEmail(anyString())).thenReturn(null);
        when(this.userService.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.tenantRepository.save(any(TenantEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        TenantEntity tenantEntity1 = this.tenantService.create(tenantEntity);
        assertEquals(true, tenantEntity1.isActive());
    }

    /**
     * Update user exists.
     */
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

    /**
     * Update tenant user not exists.
     */
    @Test
    @DisplayName("Test Create New tenant without existing user")
    void updateTenantUserNotExists() {
        TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
        tenantEntity.setActive(true);

        when(this.userService.findByEmail(anyString())).thenReturn(null);
        when(this.userService.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.tenantRepository.save(any(TenantEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        TenantEntity tenantEntity1 = this.tenantService.create(tenantEntity);
        assertEquals(true, tenantEntity1.isActive());
    }

    /**
     * Find by id.
     */
    @Test
    @DisplayName("Test Tenant read by ID")
    void findById() {
        TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
        when(this.tenantRepository.findById(any(UUID.class))).thenReturn(Optional.of(tenantEntity));
        tenantEntity = this.tenantService.findById(UUID.randomUUID());
        assertNotNull(tenantEntity);

        when(this.tenantRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        tenantEntity = this.tenantService.findById(UUID.randomUUID());
        assertNull(tenantEntity);
    }

    /**
     * Find by id all.
     */
    @Test
    @DisplayName("Test read all tenants")
    void findByIdAll() {
        try (MockedStatic<CurrentUserContext> mockedStatic = mockStatic(CurrentUserContext.class)) {
            // Define the behavior of the static method
            mockedStatic.when(CurrentUserContext::getCurrentTenant).thenReturn(UUID.randomUUID());

            TenantEntity tenantEntity = Instancio.create(TenantEntity.class);
            ReflectionTestUtils.setField(this.tenantService, "systemTenantEntity", tenantEntity);
            List<TenantEntity> tenantEntities = Instancio.ofList(TenantEntity.class).size(5).create();
            doReturn(true).when(this.tenantService).isSystemTenant(any(UUID.class));
            when(this.tenantRepository.findAll()).thenReturn(tenantEntities);
            List<TenantEntity> tenantEntities1 = this.tenantService.findAll();
            assertEquals(tenantEntities, tenantEntities1);

            doReturn(false).when(this.tenantService).isSystemTenant(any(UUID.class));
            when(this.tenantRepository.findById(any(UUID.class))).thenReturn(Optional.of( Instancio.create(TenantEntity.class)));
            tenantEntities1 = this.tenantService.findAll();
            assertEquals(1, tenantEntities1.size());
        }
    }

    /**
     * Find by pagination ok.
     */
    @Test
    @DisplayName("Test find findByPagination role OK")
    void findByPaginationOk() {
        try (MockedStatic<CurrentUserContext> mockedStatic = mockStatic(CurrentUserContext.class)) {
            // Define the behavior of the static method
            mockedStatic.when(CurrentUserContext::getCurrentTenant).thenReturn(UUID.randomUUID());
            doReturn(true).when(this.tenantService).isSystemTenant(any(UUID.class));
            List<TenantEntity> tenantEntities = Instancio.ofList(TenantEntity.class).size(11).create();
            PageImpl<TenantEntity> entityPage =
                    new PageImpl<>(tenantEntities);
            when(this.tenantRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

            Page<TenantEntity> tenantEntities1 = this.tenantService.findByPagination(0, 11);

            assertEquals(tenantEntities, tenantEntities1.getContent());
            assertEquals(1, tenantEntities1.getTotalPages());


            doReturn(false).when(this.tenantService).isSystemTenant(any(UUID.class));

            when(this.tenantRepository.findById( any(UUID.class))).thenReturn(Optional.of( Instancio.create(TenantEntity.class)));

            tenantEntities1 = this.tenantService.findByPagination(0, 11);

            assertEquals(1, tenantEntities1.getContent().size());
        }
    }
}