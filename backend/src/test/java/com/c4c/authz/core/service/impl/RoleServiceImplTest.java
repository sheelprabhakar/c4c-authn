package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.repository.RoleRepository;
import com.c4c.authz.core.service.api.SystemTenantService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The type Role service impl test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoleServiceImplTest {
    /**
     * The System tenant service.
     */
    @Mock
    SystemTenantService systemTenantService;
    /**
     * The Role service.
     */
    @InjectMocks
    RoleServiceImpl roleService;
    /**
     * The Role repository.
     */
    @Mock
    RoleRepository roleRepository;

    /**
     * Create ok.
     */
    @Test
    @DisplayName("Test Create new role OK")
    void createOk() {
        when(this.roleRepository.save(any(RoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RoleEntity roleEntity = Instancio.create(RoleEntity.class);
        RoleEntity roleEntity1 = this.roleService.create(roleEntity);
        assertEquals(roleEntity, roleEntity1);
    }

    /**
     * Update ok.
     */
    @Test
    @DisplayName("Test update role OK")
    void updateOK() {
        when(this.roleRepository.save(any(RoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RoleEntity roleEntity = Instancio.create(RoleEntity.class);
        RoleEntity roleEntity1 = this.roleService.update(roleEntity);
        assertEquals(roleEntity, roleEntity1);
    }

    /**
     * Find by id ok.
     */
    @Test
    @DisplayName("Test find by id role OK")
    void findByIdOk() {
        RoleEntity roleEntity = Instancio.create(RoleEntity.class);
        when(this.roleRepository.findById(any(UUID.class))).thenReturn(Optional.of(roleEntity));

        RoleEntity roleEntity1 = this.roleService.findById(UUID.randomUUID());
        assertEquals(roleEntity, roleEntity1);
    }

    /**
     * Find all ok.
     */
    @Test
    @DisplayName("Test find all role OK")
    void findAllOk() {
        try (MockedStatic<CurrentUserContext> mockedStatic = mockStatic(CurrentUserContext.class)) {
            // Define the behavior of the static method
            mockedStatic.when(CurrentUserContext::getCurrentTenant).thenReturn(UUID.randomUUID());
            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(true);
            List<RoleEntity> roleEntities = Instancio.ofList(RoleEntity.class).size(5).create();
            when(this.roleRepository.findAll()).thenReturn(roleEntities);

            List<RoleEntity> roleEntities1 = this.roleService.findAll();
            assertEquals(5, roleEntities1.size());
            assertEquals(roleEntities, roleEntities1);

            when(this.roleRepository.findAll()).thenReturn(Collections.emptyList());
            roleEntities1 = this.roleService.findAll();
            assertEquals(0, roleEntities1.size());

            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(false);
            roleEntities = Instancio.ofList(RoleEntity.class).size(5).create();
            when(this.roleRepository.findAllByTenantId(any(UUID.class))).thenReturn(roleEntities);

            roleEntities1 = this.roleService.findAll();
            assertEquals(5, roleEntities1.size());
            assertEquals(roleEntities, roleEntities1);

            when(this.roleRepository.findAllByTenantId(any(UUID.class))).thenReturn(Collections.emptyList());
            roleEntities1 = this.roleService.findAll();
            assertEquals(0, roleEntities1.size());
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
            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(true);
            List<RoleEntity> roleEntities = Instancio.ofList(RoleEntity.class).size(11).create();
            PageImpl<RoleEntity> entityPage =
                    new PageImpl<>(roleEntities);
            when(this.roleRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

            Page<RoleEntity> roleEntities1 = this.roleService.findByPagination(0, 11);

            assertEquals(roleEntities, roleEntities1.getContent());
            assertEquals(1, roleEntities1.getTotalPages());


            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(false);
            roleEntities = Instancio.ofList(RoleEntity.class).size(11).create();
            entityPage =
                    new PageImpl<>(roleEntities);
            when(this.roleRepository.findAllByTenantId(any(Pageable.class), any(UUID.class))).thenReturn(entityPage);

            roleEntities1 = this.roleService.findByPagination(0, 11);

            assertEquals(roleEntities, roleEntities1.getContent());
            assertEquals(1, roleEntities1.getTotalPages());
        }
    }

    /**
     * Delete by id ok.
     */
    @Test
    @DisplayName("Test deleteBy id OK")
    void deleteByIdOK() {
        doNothing().when(this.roleRepository).deleteById(any(UUID.class));
        UUID randomUUID = UUID.randomUUID();
        this.roleService.deleteById(randomUUID);
        verify(this.roleRepository, times(1)).deleteById(randomUUID);
    }

    /**
     * Delete all by id ok.
     */
    @Test
    @DisplayName("Test deleteAllById OK")
    void deleteAllByIdOK() {
        doNothing().when(this.roleRepository).deleteAllById(ArgumentMatchers.<UUID>anyList());
        List<UUID> randomUUIDs = Instancio.ofList(UUID.class).size(5).create();
        this.roleService.deleteAllById(randomUUIDs);
        verify(this.roleRepository, times(1)).deleteAllById(randomUUIDs);
    }
}