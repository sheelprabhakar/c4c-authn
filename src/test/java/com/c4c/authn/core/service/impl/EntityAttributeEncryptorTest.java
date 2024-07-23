package com.c4c.authn.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class EntityAttributeEncryptorTest {

  private EntityAttributeEncryptor entityAttributeEncryptorUnderTest;

  @BeforeEach
  void setUp() {
    entityAttributeEncryptorUnderTest = new EntityAttributeEncryptor();
    ReflectionTestUtils.setField(entityAttributeEncryptorUnderTest, "secret", "secret");
  }

  @Test
  void testConvertToDatabaseColumn() {
    String encText =
        entityAttributeEncryptorUnderTest.convertToDatabaseColumn("attribute");
    String s = entityAttributeEncryptorUnderTest.convertToEntityAttribute(encText);
    assertEquals("attribute", s);
  }
}
