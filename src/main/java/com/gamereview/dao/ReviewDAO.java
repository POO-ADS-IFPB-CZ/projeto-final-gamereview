package com.gamereview.dao;

import com.gamereview.model.Review;
import com.gamereview.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public void save(Review review) {
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

    public List<Review> listReviews() {
        String sql = "SELECT * from review ORDER BY review_date desc";
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setGameTitle(rs.getString("game_title"));
                review.setPlatform(rs.getString("platform"));
                review.setRating(rs.getDouble("rating"));
                review.setText(rs.getString("text"));
                review.setReviewDate(rs.getDate("review_date").toLocalDate());
                review.setProfileId(rs.getInt("profile_id"));

                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println(reviews);
        return reviews;
    }
}