package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.RestAclEntity;
import com.c4c.authz.core.repository.RestAclRepository;
import com.c4c.authz.core.service.api.SystemTenantService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

/**
 * The type Rest acl service impl test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestAclServiceImplTest {
    /**
     * The Rest acl service.
     */
    @InjectMocks
  RestAclServiceImpl restAclService;
    /**
     * The Rest acl repository.
     */
    @Mock
  RestAclRepository restAclRepository;

    /**
     * The System tenant service.
     */
    @Mock
    SystemTenantService systemTenantService;

    /**
     * Create ok.
     */
    @Test
    @DisplayName("Test Create new REST resource OK")
    void createOk() {
        when(this.restAclRepository.save(any(RestAclEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RestAclEntity restAclEntity = Instancio.create(RestAclEntity.class);
        RestAclEntity restAclEntity1 = this.restAclService.create(restAclEntity);
        assertEquals(restAclEntity, restAclEntity1);
    }

    /**
     * Update ok.
     */
    @Test
    @DisplayName("Test update REST resource OK")
    void updateOK() {
        when(this.restAclRepository.save(any(RestAclEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RestAclEntity restAclEntity = Instancio.create(RestAclEntity.class);
        RestAclEntity restAclEntity1 = this.restAclService.update(restAclEntity);
        assertEquals(restAclEntity, restAclEntity1);
    }

    /**
     * Find by id ok.
     */
    @Test
    @DisplayName("Test find by id REST resource OK")
    void findByIdOk() {
        RestAclEntity restAclEntity = Instancio.create(RestAclEntity.class);
        when(this.restAclRepository.findById(any(UUID.class))).thenReturn(Optional.of(restAclEntity));

        RestAclEntity restAclEntity1 = this.restAclService.findById(UUID.randomUUID());
        assertEquals(restAclEntity, restAclEntity1);
    }

    /**
     * Find all ok.
     */
    @Test
    @DisplayName("Test find all REST resource OK")
    void findAllOk() {
        try (MockedStatic<CurrentUserContext> mockedStatic = mockStatic(CurrentUserContext.class)) {
            // Define the behavior of the static method
            mockedStatic.when(CurrentUserContext::getCurrentTenantId).thenReturn(UUID.randomUUID());
            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(true);
            List<RestAclEntity> restResourceEntities = Instancio.ofList(RestAclEntity.class).size(5).create();
            when(this.restAclRepository.findAll()).thenReturn(restResourceEntities);
            List<RestAclEntity> restResourceEntities1 = this.restAclService.findAll();
            assertEquals(5, restResourceEntities1.size());
            assertEquals(restResourceEntities, restResourceEntities1);

            when(this.restAclRepository.findAll()).thenReturn(Collections.emptyList());
            restResourceEntities1 = this.restAclService.findAll();
            assertEquals(0, restResourceEntities1.size());


            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(false);
            restResourceEntities = Instancio.ofList(RestAclEntity.class).size(5).create();
            when(this.restAclRepository.findAllByTenantId(any(UUID.class))).thenReturn(restResourceEntities);
            restResourceEntities1 = this.restAclService.findAll();
            assertEquals(5, restResourceEntities1.size());
            assertEquals(restResourceEntities, restResourceEntities1);

            when(this.restAclRepository.findAllByTenantId(any(UUID.class))).thenReturn(Collections.emptyList());
            restResourceEntities1 = this.restAclService.findAll();
            assertEquals(0, restResourceEntities1.size());

        }
    }

    /**
     * Find by pagination ok.
     */
    @Test
    @DisplayName("Test find findByPagination REST resource OK")
    void findByPaginationOk() {
        try (MockedStatic<CurrentUserContext> mockedStatic = mockStatic(CurrentUserContext.class)) {
            // Define the behavior of the static method
            mockedStatic.when(CurrentUserContext::getCurrentTenantId).thenReturn(UUID.randomUUID());
            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(true);
            List<RestAclEntity> restResourceEntities = Instancio.ofList(RestAclEntity.class).size(11).create();
            PageImpl<RestAclEntity> entityPage =
                    new PageImpl<>(restResourceEntities);
            when(this.restAclRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

            Page<RestAclEntity> restResourceEntities1 = this.restAclService.findByPagination(0,11);

            assertEquals(restResourceEntities, restResourceEntities1.getContent());
            assertEquals(1, restResourceEntities1.getTotalPages());

            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(false);
            restResourceEntities = Instancio.ofList(RestAclEntity.class).size(11).create();
            entityPage =
                    new PageImpl<>(restResourceEntities);
            when(this.restAclRepository.findAllByTenantId(any(Pageable.class), any(UUID.class))).thenReturn(entityPage);

            restResourceEntities1 = this.restAclService.findByPagination(0,11);

            assertEquals(restResourceEntities, restResourceEntities1.getContent());
            assertEquals(1, restResourceEntities1.getTotalPages());
        }
    }

    /**
     * Delete by id ok.
     */
    @Test
    @DisplayName("Test deleteBy id OK")
    void deleteByIdOK() {
        doNothing().when(this.restAclRepository).deleteById(any(UUID.class));
        UUID randomUUID = UUID.randomUUID();
        this.restAclService.deleteById(randomUUID);
        verify(this.restAclRepository, times(1)).deleteById(randomUUID);
    }

    /**
     * Delete all by id ok.
     */
    @Test
    @DisplayName("Test deleteAllById OK")
    void deleteAllByIdOK() {
        doNothing().when(this.restAclRepository).deleteAllById(ArgumentMatchers.<UUID>anyList());
        List<UUID> randomUUIDs = Instancio.ofList(UUID.class).size(5).create();
        this.restAclService.deleteAllById(randomUUIDs);
        verify(this.restAclRepository, times(1)).deleteAllById(randomUUIDs);
    }
}