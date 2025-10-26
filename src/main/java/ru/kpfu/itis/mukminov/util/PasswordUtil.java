package ru.kpfu.itis.mukminov.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String GLOBAL_SALT = ConfigUtil.getGlobalSalt();

    private PasswordUtil() {}

    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String userSalt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(GLOBAL_SALT.getBytes(StandardCharsets.UTF_8));

            md.update(userSalt.getBytes(StandardCharsets.UTF_8));

            md.update(password.getBytes(StandardCharsets.UTF_8));

            byte[] hashedPassword = md.digest();

            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при хешировании пароля", e);
        }
    }

    public static boolean verifyPassword(String password, String userSalt, String hash) {
        String hashedPassword = hashPassword(password, userSalt);
        return hashedPassword.equals(hash);
    }
}
