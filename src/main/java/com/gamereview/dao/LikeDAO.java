package com.gamereview.dao;

import com.gamereview.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDAO {

    public void addLike(int userId, int reviewId) {
        String sql = "INSERT INTO review_likes (user_id, review_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, reviewId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeLike(int userId, int reviewId) {
        String sql = "DELETE FROM review_likes WHERE user_id = ? AND review_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, reviewId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLikedByUser(int userId, int reviewId) {
        String sql = "SELECT 1 FROM review_likes WHERE user_id = ? AND review_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, reviewId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se encontrou o like
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countLikes(int reviewId) {
        String sql = "SELECT COUNT(*) FROM review_likes WHERE review_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna a quantidade de likes
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}