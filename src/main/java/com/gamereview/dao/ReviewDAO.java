package com.gamereview.dao;

import com.gamereview.model.Review;
import com.gamereview.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewDAO {

    public void salvar(Review review) {
        String sql = "INSERT INTO review (game_title, platform, rating, text, review_date, profile_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, review.getGameTitle());
            stmt.setString(2, review.getPlatform());
            stmt.setDouble(3, review.getRating());
            stmt.setString(4, review.getText());

            stmt.setDate(5, Date.valueOf(review.getReviewDate()));
            stmt.setInt(6, review.getProfileId());

            stmt.executeUpdate();
            System.out.println("Review salva");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar review.", e);
        }
    }
}