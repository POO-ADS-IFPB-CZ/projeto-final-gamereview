package com.gamereview.dao;

import com.gamereview.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDAO {

    public void addLike(int profileId, int reviewId) {
        String sql = "INSERT INTO likes (profile_id, review_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profileId);
            stmt.setInt(2, reviewId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeLike(int profileId, int reviewId) {
        String sql = "DELETE FROM likes WHERE profile_id = ? AND review_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profileId);
            stmt.setInt(2, reviewId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLikedByUser(int profileId, int reviewId) {
        String sql = "SELECT 1 FROM likes WHERE profile_id = ? AND review_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profileId);
            stmt.setInt(2, reviewId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countLikes(int reviewId) {
        String sql = "SELECT COUNT(*) FROM likes WHERE review_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}