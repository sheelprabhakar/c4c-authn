package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CryptoUtils;
import com.c4c.authz.common.exception.CustomException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * The type Entity attribute encryptor.
 */
@Slf4j
@Component
@Converter
public class EntityAttributeEncryptor implements AttributeConverter<String, String> {
    /**
     * The constant ENCRYPT_ALGO.
     */
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    /**
     * The constant TAG_LENGTH_BIT.
     */
    private static final int TAG_LENGTH_BIT = 128;
    /**
     * The constant IV_LENGTH_BYTE.
     */
    private static final int IV_LENGTH_BYTE = 12;
    /**
     * The constant SALT_LENGTH_BYTE.
     */
    private static final int SALT_LENGTH_BYTE = 16;
    /**
     * The constant UTF_8.
     */
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    /**
     * The constant ATTRIBUTE_ENCRYPTION_EXCEPTION.
     */
    public static final String ATTRIBUTE_ENCRYPTION_EXCEPTION =
            "Entity attribute encryption exception";
    /**
     * The constant secret.
     */
    private static String secret;

    /**
     * Sets secret.
     *
     * @param s the s
     */
    @Autowired
    public void setSecret(@Value("${security.db.encryption.secret-key:b7ynahtDw6vqj!5a}") final String s) {
        EntityAttributeEncryptor.secret = s;
    }

    /**
     * Convert to database column string.
     *
     * @param attribute the attribute
     * @return the string
     */
    @Override
    public String convertToDatabaseColumn(final String attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return encrypt(attribute.getBytes(StandardCharsets.UTF_8), secret);
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
        if (dbData == null) {
            return null;
        }
        try {
            return decrypt(dbData, secret);
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
     * @throws NoSuchPaddingException             the no such padding exception
     * @throws NoSuchAlgorithmException           the no such algorithm exception
     * @throws InvalidKeySpecException            the invalid key spec exception
     * @throws IllegalBlockSizeException          the illegal block size exception
     * @throws BadPaddingException                the bad padding exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     * @throws InvalidKeyException                the invalid key exception
     */
    public static String encrypt(final byte[] pText, final String password)
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

    /**
     * Decrypt string.
     *
     * @param cText    the c text
     * @param password the password
     * @return the string
     * @throws NoSuchAlgorithmException           the no such algorithm exception
     * @throws InvalidKeySpecException            the invalid key spec exception
     * @throws NoSuchPaddingException             the no such padding exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     * @throws InvalidKeyException                the invalid key exception
     * @throws IllegalBlockSizeException          the illegal block size exception
     * @throws BadPaddingException                the bad padding exception
     */
// we need the same password, salt and iv to decrypt it
    private static String decrypt(final String cText, final String password)
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
