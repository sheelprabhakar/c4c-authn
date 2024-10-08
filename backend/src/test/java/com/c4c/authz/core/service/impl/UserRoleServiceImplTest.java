package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.c4c.authz.core.entity.UserRoleEntity;
import com.c4c.authz.core.entity.UserRoleId;
import com.c4c.authz.core.repository.UserRoleRepository;
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
 * The type User role service impl test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserRoleServiceImplTest {
  /**
   * The User role service.
   */
  @InjectMocks
  UserRoleServiceImpl userRoleService;
  /**
   * The User role repository.
   */
  @Mock
  UserRoleRepository userRoleRepository;

  /**
   * Create ok.
   */
  @Test
  @DisplayName("Test Create new user role OK")
  void createOk() {
    when(this.userRoleRepository.save(any(UserRoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);
    UserRoleEntity roleEntity = Instancio.create(UserRoleEntity.class);
    UserRoleEntity roleEntity1 = this.userRoleService.create(roleEntity);
    assertEquals(roleEntity, roleEntity1);
  }

  /**
   * Update ok.
   */
  @Test
  @DisplayName("Test update user role OK")
  void updateOK() {
    when(this.userRoleRepository.save(any(UserRoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);
    UserRoleEntity roleEntity = Instancio.create(UserRoleEntity.class);
    UserRoleEntity roleEntity1 = this.userRoleService.update(roleEntity);
    assertEquals(roleEntity, roleEntity1);
  }

  /**
   * Find by id ok.
   */
  @Test
  @DisplayName("Test find by id user role OK")
  void findByIdOk() {
    UserRoleEntity roleEntity = Instancio.create(UserRoleEntity.class);
    when(this.userRoleRepository.findById(any(UserRoleId.class))).thenReturn(Optional.of(roleEntity));

    UserRoleEntity roleEntity1 = this.userRoleService.findById(new UserRoleId());
    assertEquals(roleEntity, roleEntity1);
  }

  /**
   * Find all ok.
   */
  @Test
  @DisplayName("Test find all user role OK")
  void findAllOk() {
    List<UserRoleEntity> roleEntities = Instancio.ofList(UserRoleEntity.class).size(5).create();
    when(this.userRoleRepository.findAll()).thenReturn(roleEntities);

    List<UserRoleEntity> roleEntities1 = this.userRoleService.findAll();
    assertEquals(5, roleEntities1.size());
    assertEquals(roleEntities, roleEntities1);

    when(this.userRoleRepository.findAll()).thenReturn(Collections.emptyList());
    roleEntities1 = this.userRoleService.findAll();
    assertEquals(0, roleEntities1.size());
  }

  /**
   * Find by pagination ok.
   */
  @Test
  @DisplayName("Test find findByPagination user role OK")
  void findByPaginationOk() {
    List<UserRoleEntity> roleEntities = Instancio.ofList(UserRoleEntity.class).size(11).create();
    PageImpl<UserRoleEntity> entityPage =
        new PageImpl<>(roleEntities);
    when(this.userRoleRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

    Page<UserRoleEntity> roleEntities1 = this.userRoleService.findByPagination(0, 11);

    assertEquals(roleEntities, roleEntities1.getContent());
    assertEquals(1, roleEntities1.getTotalPages());
  }

  /**
   * Delete by id ok.
   */
  @Test
  @DisplayName("Test deleteBy id OK")
  void deleteByIdOK() {
    doNothing().when(this.userRoleRepository).deleteById(any(UserRoleId.class));
    UserRoleId userRoleId = new UserRoleId();
    this.userRoleService.deleteById(userRoleId);
    verify(this.userRoleRepository, times(1)).deleteById(userRoleId);
  }

  /**
   * Delete all by id ok.
   */
  @Test
  @DisplayName("Test deleteAllById OK")
  void deleteAllByIdOK() {
    doNothing().when(this.userRoleRepository).deleteAllById(ArgumentMatchers.<UserRoleId>anyList());
    List<UserRoleId> userRoleIds = Instancio.ofList(UserRoleId.class).size(5).create();
    this.userRoleService.deleteAllById(userRoleIds);
    verify(this.userRoleRepository, times(1)).deleteAllById(userRoleIds);
  }

  /**
   * Find by user id ok.
   */
  @Test
  @DisplayName("Test find by user id OK")
  void findByUserIdOk() {
    List<UserRoleEntity> userRoleEntities = Instancio.ofList(UserRoleEntity.class).size(5).create();
    UUID userId = UUID.randomUUID();
    when(this.userRoleRepository.findByUserId(userId)).thenReturn(userRoleEntities);

    List<UserRoleEntity> userRoleEntities1 = this.userRoleService.findByUserId(userId);
    assertEquals(5, userRoleEntities1.size());
    assertEquals(userRoleEntities, userRoleEntities1);

    when(this.userRoleRepository.findByUserId(userId)).thenReturn(Collections.emptyList());
    userRoleEntities1 = this.userRoleService.findByUserId(userId);
    assertEquals(0, userRoleEntities1.size());
  }
}