package com.c4c.authz.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The type Crypto utils test.
 */
class CryptoUtilsTest {

  /**
   * Gets random nonce.
   */
  @Test
  @DisplayName("Test get random once, only length")
  void getRandomNonce() {
    assertEquals(12, CryptoUtils.getRandomNonce(12).length);
  }

  /**
   * Gets aes key.
   *
   * @throws NoSuchAlgorithmException the no such algorithm exception
   */
  @Test
  @DisplayName("Test aes key, test length based on key size")
  void getAESKey() throws NoSuchAlgorithmException {
    assertEquals(16, CryptoUtils.getAESKey(128).getEncoded().length);
    assertEquals(32, CryptoUtils.getAESKey(256).getEncoded().length);
    assertEquals(24, CryptoUtils.getAESKey(192).getEncoded().length);
  }

  /**
   * Gets aes key from password.
   *
   * @throws NoSuchAlgorithmException the no such algorithm exception
   * @throws InvalidKeySpecException  the invalid key spec exception
   */
  @Test
  void getAESKeyFromPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword("sheel".toCharArray(),
        "prabha".getBytes(StandardCharsets.UTF_8));
    assertEquals("AES", aesKeyFromPassword.getAlgorithm());
    assertEquals("RAW", aesKeyFromPassword.getFormat());
  }

  /**
   * Hex.
   */
  @Test
  void hex() {
    String hex = CryptoUtils.hex("hex".getBytes(StandardCharsets.UTF_8));
    assertEquals("686578", hex);
  }

  /**
   * Hex with block size.
   */
  @Test
  void hexWithBlockSize() {
    String hex = CryptoUtils.hexWithBlockSize("hex".getBytes(StandardCharsets.UTF_8), 2);
    assertEquals("[6865, 78]", hex);
  }
}