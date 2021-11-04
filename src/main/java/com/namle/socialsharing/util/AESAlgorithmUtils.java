package com.namle.socialsharing.util;

import com.namle.socialsharing.config.AppConfig;
import com.namle.socialsharing.exception.AESAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Component
public class AESAlgorithmUtils {
    @Autowired
    private AppConfig appConfig;

    private static final String AES_MODE = "AES/GCM/NoPadding";
    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;

    public Optional<String> encrypt(String plainText) {
        String cipherText = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(appConfig.getSecretKey().getBytes(), "AES");
            byte[] initVector = new byte[GCM_IV_LENGTH];
            Cipher cipher = Cipher.getInstance(AES_MODE);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, initVector);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
            cipherText = Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException ex) {
            log.error("AESAlgorithmUtils - Error while encrypting", ex);
        }
        return Optional.ofNullable(cipherText);
    }
    public Optional<String> decrypt(String cipherText) {
        String plainText = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(appConfig.getSecretKey().getBytes(), "AES");
            byte[] initVector = new byte[GCM_IV_LENGTH];
            Cipher cipher = Cipher.getInstance(AES_MODE);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, initVector);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
            plainText = new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException ex) {
            log.error("AESAlgorithmUtils - Error while decrypting", ex);
        }
        return Optional.ofNullable(plainText);
    }

    public String processGenEncryptedKey(String userName, String shareKey) {
        String plainText = userName + "-" + shareKey;
        Optional<String> optionalKey = encrypt(plainText);
        if(optionalKey.isPresent()) {
            return optionalKey.get();
        }
        throw new AESAlgorithmException("AES Algorithm exception");
    }

    public String[] decodeToGetUserNameAndShareKey(String encryptedKey) {
        Optional<String> optionalPlainText = decrypt(encryptedKey);
        if(optionalPlainText.isPresent()) {
            return optionalPlainText.get().split("-");
        }
        throw new AESAlgorithmException("AES Algorithm exception");
    }

    public String encodeNam(String plainText) {
        Optional<String> optionalKey = encrypt(plainText);
        if(optionalKey.isPresent()) {
            return optionalKey.get();
        }
        throw new AESAlgorithmException("AES Algorithm exception");
    }

    public String decodeNam(String encryptedKey) {
        Optional<String> optionalPlainText = decrypt(encryptedKey);
        if(optionalPlainText.isPresent()) {
            return optionalPlainText.get();
        }
        throw new AESAlgorithmException("AES Algorithm exception");
    }
}
