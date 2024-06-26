package com.c4c.authn.core.service.impl;

import jakarta.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Entity attribute encryptor.
 */
@Slf4j
@Component
public class EntityAttributeEncryptor implements AttributeConverter<String, String> {
  /**
   * The constant AES.
   */
  private static final String AES = "AES";
  /**
   * The Secret.
   */
  @Value("${security.db.encryption.secret-key:b7ynahtDw6vqj!5a}")
  private String secret;

  /**
   * The Key.
   */
  private final Key key;
  /**
   * The Cipher.
   */
  private final Cipher cipher;

  /**
   * Instantiates a new Entity attribute encryptor.
   *
   * @throws Exception the exception
   */
  public EntityAttributeEncryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
    key = new SecretKeySpec(secret.getBytes(), AES);
    cipher = Cipher.getInstance(AES);
  }

  /**
   * Convert to database column string.
   *
   * @param attribute the attribute
   * @return the string
   */
  @Override
  public String convertToDatabaseColumn(final String attribute) {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
    } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
      log.error("Entity attribute encryption exception", e);
      throw new IllegalStateException(e);
    }
  }

  /**
   * Convert to entity attribute string.
   *
   * @param dbData the db data
   * @return the string
   */
  @Override
  public String convertToEntityAttribute(final String dbData) {
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
    } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
      log.error("Entity attribute encryption exception", e);
      throw new IllegalStateException(e);
    }
  }
}
