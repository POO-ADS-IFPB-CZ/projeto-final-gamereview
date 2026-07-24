package com.gamereview.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             InputStream input = DatabaseInitializer.class
                     .getClassLoader()
                     .getResourceAsStream("schema.sql")) {

            if (input == null) {
                throw new RuntimeException("schema.sql not found");
            }

            String sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            for (String comando : sql.split(";")) {
                String comandoLimpo = comando.trim();
                if (!comandoLimpo.isEmpty()) {
                    stmt.execute(comandoLimpo);
                }
            }

            System.out.println("Db started successfully");

        } catch (Exception e) {
            System.out.println("Error when trying to initialize db: " + e.getMessage());
            e.printStackTrace();
        }
    }
}