package ru.kpfu.itis.mukminov.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigUtil.class.getResourceAsStream("/app.properties")) {
            if (input == null) {
                throw new RuntimeException("Не удалось найти файл app.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке конфигурации", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getGlobalSalt() {
        return properties.getProperty("security.global.salt");
    }
}
