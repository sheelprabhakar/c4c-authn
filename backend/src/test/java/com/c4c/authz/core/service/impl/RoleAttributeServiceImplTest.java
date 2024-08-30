package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.entity.RoleAttributeId;
import com.c4c.authz.core.repository.RoleAttributeRepository;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * The type Role attribute service impl test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoleAttributeServiceImplTest {
  /**
   * The Role attribute service.
   */
  @InjectMocks
    RoleAttributeServiceImpl roleAttributeService;
  /**
   * The Role attribute repository.
   */
  @Mock
    RoleAttributeRepository roleAttributeRepository;

  /**
   * Create ok.
   */
  @Test
    @DisplayName("Test Create new role attribute OK")
    void createOk() {
        when(this.roleAttributeRepository.save(any(RoleAttributeEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RoleAttributeEntity roleAttributeEntity = Instancio.create(RoleAttributeEntity.class);
        RoleAttributeEntity roleAttributeEntity1 = this.roleAttributeService.create(roleAttributeEntity);
        assertEquals(roleAttributeEntity, roleAttributeEntity1);
    }

  /**
   * Update ok.
   */
  @Test
    @DisplayName("Test update role attribute OK")
    void updateOK() {
        when(this.roleAttributeRepository.save(any(RoleAttributeEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RoleAttributeEntity roleAttributeEntity = Instancio.create(RoleAttributeEntity.class);
        RoleAttributeEntity roleAttributeEntity1 = this.roleAttributeService.update(roleAttributeEntity);
        assertEquals(roleAttributeEntity, roleAttributeEntity1);
    }

  /**
   * Find by id ok.
   */
  @Test
    @DisplayName("Test find by id role attribute OK")
    void findByIdOk() {
        RoleAttributeEntity roleAttributeEntity = Instancio.create(RoleAttributeEntity.class);
        when(this.roleAttributeRepository.findById(any(RoleAttributeId.class))).thenReturn(
                Optional.of(roleAttributeEntity));

        RoleAttributeEntity roleAttributeEntity1 = this.roleAttributeService.findById(new RoleAttributeId());
        assertEquals(roleAttributeEntity, roleAttributeEntity1);
    }

  /**
   * Find all ok.
   */
  @Test
    @DisplayName("Test find all role attribute OK")
    void findAllOk() {
        List<RoleAttributeEntity> roleAttributeEntities = Instancio.ofList(RoleAttributeEntity.class).size(5).create();
        when(this.roleAttributeRepository.findAll()).thenReturn(roleAttributeEntities);

        List<RoleAttributeEntity> roleAttributeEntities1 = this.roleAttributeService.findAll();
        assertEquals(5, roleAttributeEntities1.size());
        assertEquals(roleAttributeEntities, roleAttributeEntities1);

        when(this.roleAttributeRepository.findAll()).thenReturn(Collections.emptyList());
        roleAttributeEntities1 = this.roleAttributeService.findAll();
        assertEquals(0, roleAttributeEntities1.size());
    }

  /**
   * Find all by role id ok.
   */
  @Test
    @DisplayName("Test find all role attribute by role id OK")
    void findAllByRoleIdOk() {
        List<RoleAttributeEntity> roleAttributeEntities = Instancio.ofList(RoleAttributeEntity.class).size(5).create();
        when(this.roleAttributeRepository.findAllByRoleId(any(UUID.class))).thenReturn(roleAttributeEntities);

        List<RoleAttributeEntity> roleAttributeEntities1 = this.roleAttributeService.findAllByRoleId(UUID.randomUUID());
        assertEquals(5, roleAttributeEntities1.size());
        assertEquals(roleAttributeEntities, roleAttributeEntities1);

        when(this.roleAttributeRepository.findAllByRoleId(any(UUID.class))).thenReturn(Collections.emptyList());
        roleAttributeEntities1 = this.roleAttributeService.findAll();
        assertEquals(0, roleAttributeEntities1.size());
    }

  /**
   * Find by pagination ok.
   */
  @Test
    @DisplayName("Test find findByPagination role attribute OK")
    void findByPaginationOk() {
        List<RoleAttributeEntity> roleAttributeEntities = Instancio.ofList(RoleAttributeEntity.class).size(11).create();
        PageImpl<RoleAttributeEntity> entityPage =
                new PageImpl<>(roleAttributeEntities);
        when(this.roleAttributeRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

        Page<RoleAttributeEntity> roleAttributeEntities1 = this.roleAttributeService.findByPagination(0, 11);

        assertEquals(roleAttributeEntities, roleAttributeEntities1.getContent());
        assertEquals(1, roleAttributeEntities1.getTotalPages());
    }

  /**
   * Delete by id ok.
   */
  @Test
    @DisplayName("Test deleteBy id OK")
    void deleteByIdOK() {
        doNothing().when(this.roleAttributeRepository).deleteById(any(RoleAttributeId.class));
        RoleAttributeId roleAttributeId = new RoleAttributeId();
        this.roleAttributeService.deleteById(roleAttributeId);
        verify(this.roleAttributeRepository, times(1)).deleteById(roleAttributeId);
    }

  /**
   * Delete all by id ok.
   */
  @Test
    @DisplayName("Test deleteAllById OK")
    void deleteAllByIdOK() {
        doNothing().when(this.roleAttributeRepository).deleteAllById(ArgumentMatchers.<RoleAttributeId>anyList());
        List<RoleAttributeId> roleAttributeIds = Instancio.ofList(RoleAttributeId.class).size(5).create();
        this.roleAttributeService.deleteAllById(roleAttributeIds);
        verify(this.roleAttributeRepository, times(1)).deleteAllById(roleAttributeIds);
    }
}