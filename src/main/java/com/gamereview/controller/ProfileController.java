package com.gamereview.controller;

import com.gamereview.util.ConnectionFactory;
import com.gamereview.model.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {

    public void createProfile(Profile profile) {

        String sql = """
            INSERT INTO profile(username, email, password)
            VALUES (?, ?, ?)
            """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, profile.getUsername());
            stmt.setString(2, profile.getEmail());
            stmt.setString(3, profile.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Profile login(String email, String password) {

        String sql = """
        SELECT id, username, email, password
        FROM profile
        WHERE email = ? AND password = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {

                Profile profile = new Profile();
                profile.setId(result.getInt("id"));
                profile.setUsername(result.getString("username"));
                profile.setEmail(result.getString("email"));
                profile.setPassword(result.getString("password"));

                return profile;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
