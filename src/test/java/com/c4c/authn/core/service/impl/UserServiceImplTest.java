package com.c4c.authn.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.c4c.authn.core.entity.UserEntity;
import com.c4c.authn.core.repository.UserRepository;
import com.c4c.authn.utils.UserEntityHelper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * The type User service impl test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

  /**
   * The User service.
   */
  @InjectMocks
  UserServiceImpl userService;
  /**
   * The User repository.
   */
  @Mock
  UserRepository userRepository;

  /**
   * The User entity 1.
   */
  UserEntity userEntity1 = UserEntityHelper.getNew(UUID.randomUUID());

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    UserEntity entity = userEntity1;

    Mockito.when(userRepository.save(ArgumentMatchers.any()))
        .thenReturn(entity);

    Mockito.when(userRepository.findById(userEntity1.getId()))
        .thenReturn(Optional.of(entity));
    Mockito.when(userRepository.findById(UUID.randomUUID()))
        .thenReturn(Optional.empty());

  }

  /**
   * Test save ok.
   */
  @Test
  void test_save_ok() {
    UserEntity userEntity = this.userService.save(new UserEntity());
    assertEquals(userEntity.getId(), userEntity1.getId());
    assertNull(userEntity.getLastLogin());
  }

  /**
   * Test get ok.
   */
  @Test
  void test_get_ok() {
    UserEntity userEntity = this.userService.findById(userEntity1.getId());
    assertEquals(userEntity.getId(), userEntity1.getId());
    assertNull(userEntity.getLastLogin());

    userEntity = this.userService.findById(UUID.randomUUID());
    assertNull(userEntity);
  }

  /**
   * Test update ok.
   */
  @Test
  void test_update_ok() {
    UserEntity userEntity = this.userService.update(new UserEntity());
    assertEquals(userEntity.getId(), userEntity1.getId());
    assertEquals(UserEntityHelper.MOBILE, userEntity.getMobile());
    assertNull(userEntity.getLastLogin());
  }
}
