package com.c4c.authn.core.service.impl;

import com.c4c.authn.common.CryptoUtils;
import com.c4c.authn.common.exception.CustomException;
import jakarta.persistence.AttributeConverter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * The type Entity attribute encryptor.
 */
@Slf4j
@Component
public class EntityAttributeEncryptor implements AttributeConverter<String, String> {
  private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

  private static final int TAG_LENGTH_BIT = 128;
  private static final int IV_LENGTH_BYTE = 12;
  private static final int SALT_LENGTH_BYTE = 16;
  private static final Charset UTF_8 = StandardCharsets.UTF_8;
  public static final String ATTRIBUTE_ENCRYPTION_EXCEPTION =
      "Entity attribute encryption exception";
  /**
   * The Secret.
   */
  @Value("${security.db.encryption.secret-key:b7ynahtDw6vqj!5a}")
  private String secret;

  /**
   * Convert to database column string.
   *
   * @param attribute the attribute
   * @return the string
   */
  @Override
  public String convertToDatabaseColumn(final String attribute) {
    try {
      return encrypt(attribute.getBytes(StandardCharsets.UTF_8), this.secret);
    } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
      log.error(ATTRIBUTE_ENCRYPTION_EXCEPTION, e);
      throw new IllegalStateException(e);
    } catch (Exception e) {
      log.error(ATTRIBUTE_ENCRYPTION_EXCEPTION, e);
      throw new CustomException(ATTRIBUTE_ENCRYPTION_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
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
      return decrypt(dbData, this.secret);
    } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
      log.error(ATTRIBUTE_ENCRYPTION_EXCEPTION, e);
      throw new IllegalStateException(e);
    } catch (Exception e) {
      log.error(ATTRIBUTE_ENCRYPTION_EXCEPTION, e);
      throw new CustomException(ATTRIBUTE_ENCRYPTION_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Encrypt string.
   *
   * @param pText    the p text
   * @param password the password
   * @return the string
   * @throws Exception the exception
   */
  public static String encrypt(byte[] pText, String password)
      throws NoSuchPaddingException, NoSuchAlgorithmException,
      InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException,
      InvalidAlgorithmParameterException, InvalidKeyException {

    // 16 bytes salt
    byte[] salt = CryptoUtils.getRandomNonce(SALT_LENGTH_BYTE);

    // GCM recommended 12 bytes iv?
    byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE);

    // secret key from password
    SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

    Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

    // ASE-GCM needs GCMParameterSpec
    cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

    byte[] cipherText = cipher.doFinal(pText);

    // prefix IV and Salt to cipher text
    byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
        .put(iv)
        .put(salt)
        .put(cipherText)
        .array();

    // string representation, base64, send this string to other for decryption.
    return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);

  }

  // we need the same password, salt and iv to decrypt it
  private static String decrypt(String cText, String password)
      throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException,
      BadPaddingException {

    byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));

    // get back the iv and salt from the cipher text
    ByteBuffer bb = ByteBuffer.wrap(decode);

    byte[] iv = new byte[IV_LENGTH_BYTE];
    bb.get(iv);

    byte[] salt = new byte[SALT_LENGTH_BYTE];
    bb.get(salt);

    byte[] cipherText = new byte[bb.remaining()];
    bb.get(cipherText);

    // get back the aes key from the same password and salt
    SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

    Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

    cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

    byte[] plainText = cipher.doFinal(cipherText);

    return new String(plainText, UTF_8);

  }
}
