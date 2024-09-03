package com.c4c.authz.core.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.RestAclEntity;
import com.c4c.authz.core.entity.RoleRestAclEntity;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.entity.UserRoleEntity;
import com.c4c.authz.core.repository.TenantRepository;
import com.c4c.authz.core.service.api.RestAclService;
import com.c4c.authz.core.service.api.RoleRestAclService;
import com.c4c.authz.core.service.api.RoleService;
import com.c4c.authz.core.service.api.SystemTenantService;
import com.c4c.authz.core.service.api.UserRoleService;
import com.c4c.authz.core.service.api.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    TenantServiceImpl tenantService;
    /**
     * The Tenant repository.
     */
    @Mock
    TenantRepository tenantRepository;

    /**
     * The System tenant service.
     */
    @Mock
    SystemTenantService systemTenantService;
    /**
     * The User service.
     */
    @Mock
    UserService userService;

    /**
     * The Role service.
     */
    @Mock
    RoleService roleService;

    /**
     * The Rest acl service.
     */
    @Mock
  RestAclService restAclService;

    /**
     * The Role rest acl service.
     */
    @Mock
  RoleRestAclService roleRestAclService;

    /**
     * The User role service.
     */
    @Mock
    UserRoleService userRoleService;

    /**
     * The System tenant entity.
     */
    @Mock
    TenantEntity systemTenantEntity;

    /**
     * Sets .
     */
    @BeforeEach
    void setup() {
        when(this.roleService.create(any(RoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.restAclService.create(any(RestAclEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.roleRestAclService.create(any(RoleRestAclEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.userRoleService.create(any(UserRoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);
    }

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
            mockedStatic.when(CurrentUserContext::getCurrentTenantId).thenReturn(UUID.randomUUID());
            
            List<TenantEntity> tenantEntities = Instancio.ofList(TenantEntity.class).size(5).create();
            doReturn(true).when(this.systemTenantService).isSystemTenant(any(UUID.class));
            when(this.tenantRepository.findAll()).thenReturn(tenantEntities);
            List<TenantEntity> tenantEntities1 = this.tenantService.findAll();
            assertEquals(tenantEntities, tenantEntities1);

            doReturn(false).when(this.systemTenantService).isSystemTenant(any(UUID.class));
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
            mockedStatic.when(CurrentUserContext::getCurrentTenantId).thenReturn(UUID.randomUUID());
            doReturn(true).when(this.systemTenantService).isSystemTenant(any(UUID.class));
            List<TenantEntity> tenantEntities = Instancio.ofList(TenantEntity.class).size(11).create();
            PageImpl<TenantEntity> entityPage =
                    new PageImpl<>(tenantEntities);
            when(this.tenantRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

            Page<TenantEntity> tenantEntities1 = this.tenantService.findByPagination(0, 11);

            assertEquals(tenantEntities, tenantEntities1.getContent());
            assertEquals(1, tenantEntities1.getTotalPages());


            doReturn(false).when(this.systemTenantService).isSystemTenant(any(UUID.class));

            when(this.tenantRepository.findById( any(UUID.class))).thenReturn(Optional.of( Instancio.create(TenantEntity.class)));

            tenantEntities1 = this.tenantService.findByPagination(0, 11);

            assertEquals(1, tenantEntities1.getContent().size());
        }
    }
}