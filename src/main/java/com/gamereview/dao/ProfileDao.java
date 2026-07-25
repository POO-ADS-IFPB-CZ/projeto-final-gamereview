package com.gamereview.dao;

import com.gamereview.util.ConnectionFactory;
import com.gamereview.model.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDao {

    public boolean createProfile(Profile profile) {

        String checkEmail = """
        SELECT id
        FROM profile
        WHERE email = ?
        """;

        String sql = """
            INSERT INTO profile(username, email, password)
            VALUES (?, ?, ?)
            """;

        try (Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement checkStmt = connection.prepareStatement(checkEmail);
            checkStmt.setString(1, profile.getEmail());

            ResultSet result = checkStmt.executeQuery();

            if (result.next()) {
                return false;
            }

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, profile.getUsername());
            stmt.setString(2, profile.getEmail());
            stmt.setString(3, profile.getPassword());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Profile login(String email, String password) {

        String sql = """
        SELECT id, username, email, password
        FROM profile
        WHERE email = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {

                if (!result.getString("password").equals(password)) {
                    return null;
                }

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

    public boolean update(Profile profile) {

        String checkEmail = """
        SELECT id
        FROM profile
        WHERE email = ? AND id <> ?
        """;

        String sql = """
        UPDATE profile 
        SET username = ?, email = ?, password = ?
        WHERE id = ?
        """;

        try(Connection connection = ConnectionFactory.getConnection()) {

            PreparedStatement checkStmt = connection.prepareStatement(checkEmail);

            checkStmt.setString(1, profile.getEmail());
            checkStmt.setInt(2, profile.getId());

            ResultSet result = checkStmt.executeQuery();

            if(result.next()) {
                return false;
            }

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, profile.getUsername());
            stmt.setString(2, profile.getEmail());
            stmt.setString(3, profile.getPassword());
            stmt.setInt(4, profile.getId());

            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch(SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    public void delete(int id) {

        String sql = """
        DELETE FROM profile
        WHERE id = ?
        """;

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(SQLException e) {

            e.printStackTrace();

        }

    }
}
