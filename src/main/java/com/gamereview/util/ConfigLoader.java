package com.gamereview.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties prop = new Properties();

    static {
        try (InputStream input  = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if(input == null) {
                throw new RuntimeException("config.properties resource not found!");
            }

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
