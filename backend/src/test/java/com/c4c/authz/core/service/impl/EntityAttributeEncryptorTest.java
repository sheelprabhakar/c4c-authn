package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * The type Entity attribute encryptor test.
 */
class EntityAttributeEncryptorTest {

  /**
   * The Entity attribute encryptor under test.
   */
  private EntityAttributeEncryptor entityAttributeEncryptorUnderTest;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    entityAttributeEncryptorUnderTest = new EntityAttributeEncryptor();
    ReflectionTestUtils.setField(entityAttributeEncryptorUnderTest, "secret", "secret");
  }

  /**
   * Test convert to database column.
   */
  @Test
  void testConvertToDatabaseColumn() {
    String encText =
        entityAttributeEncryptorUnderTest.convertToDatabaseColumn("attribute");
    String s = entityAttributeEncryptorUnderTest.convertToEntityAttribute(encText);
    assertEquals("attribute", s);
  }
}
