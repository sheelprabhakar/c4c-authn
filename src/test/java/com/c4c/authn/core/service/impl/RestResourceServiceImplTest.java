package com.c4c.authn.core.service.impl;

import com.c4c.authn.core.entity.RestResourceEntity;
import com.c4c.authn.core.repository.RestResourceRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestResourceServiceImplTest {
    @InjectMocks
    RestResourceServiceImpl restResourceService;
    @Mock
    RestResourceRepository restResourceRepository;

    @Test
    @DisplayName("Test Create new REST resource OK")
    void createOk() {
        when(this.restResourceRepository.save(any(RestResourceEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RestResourceEntity restResourceEntity = Instancio.create(RestResourceEntity.class);
        RestResourceEntity restResourceEntity1 = this.restResourceService.create(restResourceEntity);
        assertEquals(restResourceEntity, restResourceEntity1);
    }

    @Test
    @DisplayName("Test update REST resource OK")
    void updateOK() {
        when(this.restResourceRepository.save(any(RestResourceEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RestResourceEntity restResourceEntity = Instancio.create(RestResourceEntity.class);
        RestResourceEntity restResourceEntity1 = this.restResourceService.update(restResourceEntity);
        assertEquals(restResourceEntity, restResourceEntity1);
    }

    @Test
    @DisplayName("Test find by id REST resource OK")
    void findByIdOk() {
        RestResourceEntity restResourceEntity = Instancio.create(RestResourceEntity.class);
        when(this.restResourceRepository.findById(any(UUID.class))).thenReturn(Optional.of(restResourceEntity));

        RestResourceEntity restResourceEntity1 = this.restResourceService.findById(UUID.randomUUID());
        assertEquals(restResourceEntity, restResourceEntity1);
    }

    @Test
    @DisplayName("Test find all REST resource OK")
    void findAllOk() {
        List<RestResourceEntity> restResourceEntities = Instancio.ofList(RestResourceEntity.class).size(5).create();
        when(this.restResourceRepository.findAll()).thenReturn(restResourceEntities);

        List<RestResourceEntity> restResourceEntities1 = this.restResourceService.findAll();
        assertEquals(5, restResourceEntities1.size());
        assertEquals(restResourceEntities, restResourceEntities1);

        when(this.restResourceRepository.findAll()).thenReturn(Collections.emptyList());
        restResourceEntities1 = this.restResourceService.findAll();
        assertEquals(0, restResourceEntities1.size());
    }

    @Test
    @DisplayName("Test find findByPagination REST resource OK")
    void findByPaginationOk() {
        List<RestResourceEntity> restResourceEntities = Instancio.ofList(RestResourceEntity.class).size(11).create();
        PageImpl<RestResourceEntity> entityPage =
                new PageImpl<>(restResourceEntities);
        when(this.restResourceRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

        Page<RestResourceEntity> restResourceEntities1 = this.restResourceService.findByPagination(0,11);

        assertEquals(restResourceEntities, restResourceEntities1.getContent());
        assertEquals(1, restResourceEntities1.getTotalPages());
    }

    @Test
    @DisplayName("Test deleteBy id OK")
    void deleteByIdOK() {
        doNothing().when(this.restResourceRepository).deleteById(any(UUID.class));
        UUID randomUUID = UUID.randomUUID();
        this.restResourceService.deleteById(randomUUID);
        verify(this.restResourceRepository, times(1)).deleteById(randomUUID);
    }

    @Test
    @DisplayName("Test deleteAllById OK")
    void deleteAllByIdOK() {
        doNothing().when(this.restResourceRepository).deleteAllById(ArgumentMatchers.<UUID>anyList());
        List<UUID> randomUUIDs = Instancio.ofList(UUID.class).size(5).create();
        this.restResourceService.deleteAllById(randomUUIDs);
        verify(this.restResourceRepository, times(1)).deleteAllById(randomUUIDs);
    }
}