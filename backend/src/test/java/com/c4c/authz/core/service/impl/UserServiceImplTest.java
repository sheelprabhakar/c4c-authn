package com.c4c.authz.core.service.impl;

import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.repository.UserRepository;
import com.c4c.authz.utils.UserEntityHelper;
import org.instancio.Instancio;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;

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

  @Mock
  PasswordEncoder passwordEncoder;

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
        .thenAnswer(i->i.getArguments()[0]);

    Mockito.when(userRepository.findById(userEntity1.getId()))
        .thenReturn(Optional.of(entity));
    Mockito.when(userRepository.findById(UUID.randomUUID()))
        .thenReturn(Optional.empty());

    Mockito.when(passwordEncoder.encode(anyString())).thenReturn("abcd");

  }

  /**
   * Test save ok.
   */
  @Test
  void test_save_ok() {
    UserEntity userEntity = Instancio.create(UserEntity.class);
    UserEntity userEntity2 = this.userService.save(userEntity);
    assertEquals(userEntity.getId(), userEntity2.getId());
    assertEquals("abcd", userEntity2.getPasswordHash());
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
    UserEntity userEntity = this.userService.update(userEntity1);
    assertEquals(userEntity.getId(), userEntity1.getId());
    assertEquals(UserEntityHelper.MOBILE, userEntity.getMobile());
    assertNull(userEntity.getLastLogin());
  }
}
