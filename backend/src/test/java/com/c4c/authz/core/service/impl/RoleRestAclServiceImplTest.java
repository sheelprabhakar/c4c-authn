package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.c4c.authz.core.entity.RoleRestAclEntity;
import com.c4c.authz.core.entity.RoleRestAclId;
import com.c4c.authz.core.repository.RoleRestAclRepository;
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
 * The type Role rest acl service impl test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoleRestAclServiceImplTest {
    /**
     * The Role rest acl service.
     */
    @InjectMocks
  RoleRestAclServiceImpl roleRestAclService;
    /**
     * The Role rest acl repository.
     */
    @Mock
  RoleRestAclRepository roleRestAclRepository;

    /**
     * Create ok.
     */
    @Test
    @DisplayName("Test Create new role attribute OK")
    void createOk() {
        when(this.roleRestAclRepository.save(any(RoleRestAclEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RoleRestAclEntity roleRestAclEntity = Instancio.create(RoleRestAclEntity.class);
        RoleRestAclEntity roleRestAclEntity1 = this.roleRestAclService.create(roleRestAclEntity);
        assertEquals(roleRestAclEntity, roleRestAclEntity1);
    }

    /**
     * Update ok.
     */
    @Test
    @DisplayName("Test update role attribute OK")
    void updateOK() {
        when(this.roleRestAclRepository.save(any(RoleRestAclEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        RoleRestAclEntity roleRestAclEntity = Instancio.create(RoleRestAclEntity.class);
        RoleRestAclEntity roleRestAclEntity1 = this.roleRestAclService.update(roleRestAclEntity);
        assertEquals(roleRestAclEntity, roleRestAclEntity1);
    }

    /**
     * Find by id ok.
     */
    @Test
    @DisplayName("Test find by id role attribute OK")
    void findByIdOk() {
        RoleRestAclEntity roleRestAclEntity = Instancio.create(RoleRestAclEntity.class);
        when(this.roleRestAclRepository.findById(any(RoleRestAclId.class))).thenReturn(
                Optional.of(roleRestAclEntity));

        RoleRestAclEntity roleRestAclEntity1 = this.roleRestAclService.findById(new RoleRestAclId());
        assertEquals(roleRestAclEntity, roleRestAclEntity1);
    }

    /**
     * Find all ok.
     */
    @Test
    @DisplayName("Test find all role attribute OK")
    void findAllOk() {
        List<RoleRestAclEntity> roleAttributeEntities = Instancio.ofList(RoleRestAclEntity.class).size(5).create();
        when(this.roleRestAclRepository.findAll()).thenReturn(roleAttributeEntities);

        List<RoleRestAclEntity> roleAttributeEntities1 = this.roleRestAclService.findAll();
        assertEquals(5, roleAttributeEntities1.size());
        assertEquals(roleAttributeEntities, roleAttributeEntities1);

        when(this.roleRestAclRepository.findAll()).thenReturn(Collections.emptyList());
        roleAttributeEntities1 = this.roleRestAclService.findAll();
        assertEquals(0, roleAttributeEntities1.size());
    }

    /**
     * Find all by role id ok.
     */
    @Test
    @DisplayName("Test find all role attribute by role id OK")
    void findAllByRoleIdOk() {
        List<RoleRestAclEntity> roleAttributeEntities = Instancio.ofList(RoleRestAclEntity.class).size(5).create();
        when(this.roleRestAclRepository.findAllByRoleId(any(UUID.class))).thenReturn(roleAttributeEntities);

        List<RoleRestAclEntity> roleAttributeEntities1 = this.roleRestAclService.findAllByRoleId(UUID.randomUUID());
        assertEquals(5, roleAttributeEntities1.size());
        assertEquals(roleAttributeEntities, roleAttributeEntities1);

        when(this.roleRestAclRepository.findAllByRoleId(any(UUID.class))).thenReturn(Collections.emptyList());
        roleAttributeEntities1 = this.roleRestAclService.findAll();
        assertEquals(0, roleAttributeEntities1.size());
    }

    /**
     * Find by pagination ok.
     */
    @Test
    @DisplayName("Test find findByPagination role attribute OK")
    void findByPaginationOk() {
        List<RoleRestAclEntity> roleAttributeEntities = Instancio.ofList(RoleRestAclEntity.class).size(11).create();
        PageImpl<RoleRestAclEntity> entityPage =
                new PageImpl<>(roleAttributeEntities);
        when(this.roleRestAclRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

        Page<RoleRestAclEntity> roleAttributeEntities1 = this.roleRestAclService.findByPagination(0, 11);

        assertEquals(roleAttributeEntities, roleAttributeEntities1.getContent());
        assertEquals(1, roleAttributeEntities1.getTotalPages());
    }

    /**
     * Delete by id ok.
     */
    @Test
    @DisplayName("Test deleteBy id OK")
    void deleteByIdOK() {
        doNothing().when(this.roleRestAclRepository).deleteById(any(RoleRestAclId.class));
        RoleRestAclId roleRestAclId = new RoleRestAclId();
        this.roleRestAclService.deleteById(roleRestAclId);
        verify(this.roleRestAclRepository, times(1)).deleteById(roleRestAclId);
    }

    /**
     * Delete all by id ok.
     */
    @Test
    @DisplayName("Test deleteAllById OK")
    void deleteAllByIdOK() {
        doNothing().when(this.roleRestAclRepository).deleteAllById(ArgumentMatchers.<RoleRestAclId>anyList());
        List<RoleRestAclId> roleRestAclIds = Instancio.ofList(RoleRestAclId.class).size(5).create();
        this.roleRestAclService.deleteAllById(roleRestAclIds);
        verify(this.roleRestAclRepository, times(1)).deleteAllById(roleRestAclIds);
    }
}