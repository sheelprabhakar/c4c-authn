package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.AttributeEntity;
import com.c4c.authz.core.repository.AttributeRepository;
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
 * The type Attribute service impl test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AttributeServiceImplTest {
    /**
     * The Attribute service.
     */
    @InjectMocks
    AttributeServiceImpl attributeService;
    /**
     * The Attribute repository.
     */
    @Mock
    AttributeRepository attributeRepository;

    @Mock
    SystemTenantService systemTenantService;
    /**
     * Create ok.
     */
    @Test
    @DisplayName("Test Create new REST resource OK")
    void createOk() {
        when(this.attributeRepository.save(any(AttributeEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        AttributeEntity attributeEntity = Instancio.create(AttributeEntity.class);
        AttributeEntity attributeEntity1 = this.attributeService.create(attributeEntity);
        assertEquals(attributeEntity, attributeEntity1);
    }

    /**
     * Update ok.
     */
    @Test
    @DisplayName("Test update REST resource OK")
    void updateOK() {
        when(this.attributeRepository.save(any(AttributeEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        AttributeEntity attributeEntity = Instancio.create(AttributeEntity.class);
        AttributeEntity attributeEntity1 = this.attributeService.update(attributeEntity);
        assertEquals(attributeEntity, attributeEntity1);
    }

    /**
     * Find by id ok.
     */
    @Test
    @DisplayName("Test find by id REST resource OK")
    void findByIdOk() {
        AttributeEntity attributeEntity = Instancio.create(AttributeEntity.class);
        when(this.attributeRepository.findById(any(UUID.class))).thenReturn(Optional.of(attributeEntity));

        AttributeEntity attributeEntity1 = this.attributeService.findById(UUID.randomUUID());
        assertEquals(attributeEntity, attributeEntity1);
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
            List<AttributeEntity> restResourceEntities = Instancio.ofList(AttributeEntity.class).size(5).create();
            when(this.attributeRepository.findAll()).thenReturn(restResourceEntities);
            List<AttributeEntity> restResourceEntities1 = this.attributeService.findAll();
            assertEquals(5, restResourceEntities1.size());
            assertEquals(restResourceEntities, restResourceEntities1);

            when(this.attributeRepository.findAll()).thenReturn(Collections.emptyList());
            restResourceEntities1 = this.attributeService.findAll();
            assertEquals(0, restResourceEntities1.size());


            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(false);
            restResourceEntities = Instancio.ofList(AttributeEntity.class).size(5).create();
            when(this.attributeRepository.findAllByTenantId(any(UUID.class))).thenReturn(restResourceEntities);
            restResourceEntities1 = this.attributeService.findAll();
            assertEquals(5, restResourceEntities1.size());
            assertEquals(restResourceEntities, restResourceEntities1);

            when(this.attributeRepository.findAllByTenantId(any(UUID.class))).thenReturn(Collections.emptyList());
            restResourceEntities1 = this.attributeService.findAll();
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
            List<AttributeEntity> restResourceEntities = Instancio.ofList(AttributeEntity.class).size(11).create();
            PageImpl<AttributeEntity> entityPage =
                    new PageImpl<>(restResourceEntities);
            when(this.attributeRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

            Page<AttributeEntity> restResourceEntities1 = this.attributeService.findByPagination(0,11);

            assertEquals(restResourceEntities, restResourceEntities1.getContent());
            assertEquals(1, restResourceEntities1.getTotalPages());

            when(this.systemTenantService.isSystemTenant(any(UUID.class))).thenReturn(false);
            restResourceEntities = Instancio.ofList(AttributeEntity.class).size(11).create();
            entityPage =
                    new PageImpl<>(restResourceEntities);
            when(this.attributeRepository.findAllByTenantId(any(Pageable.class), any(UUID.class))).thenReturn(entityPage);

            restResourceEntities1 = this.attributeService.findByPagination(0,11);

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
        doNothing().when(this.attributeRepository).deleteById(any(UUID.class));
        UUID randomUUID = UUID.randomUUID();
        this.attributeService.deleteById(randomUUID);
        verify(this.attributeRepository, times(1)).deleteById(randomUUID);
    }

    /**
     * Delete all by id ok.
     */
    @Test
    @DisplayName("Test deleteAllById OK")
    void deleteAllByIdOK() {
        doNothing().when(this.attributeRepository).deleteAllById(ArgumentMatchers.<UUID>anyList());
        List<UUID> randomUUIDs = Instancio.ofList(UUID.class).size(5).create();
        this.attributeService.deleteAllById(randomUUIDs);
        verify(this.attributeRepository, times(1)).deleteAllById(randomUUIDs);
    }
}