package com.gamereview.dao;

import com.gamereview.model.Comment;
import com.gamereview.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public void save(Comment comment) {
        String sql = "INSERT INTO comment (text, comment_date, profile_id, review_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comment.getText());
            stmt.setDate(2, Date.valueOf(comment.getCommentDate()));
            stmt.setInt(3, comment.getProfileId());
            stmt.setInt(4, comment.getReviewId());

            stmt.executeUpdate();
            System.out.println("Comentário salvo");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar comentário.", e);
        }
    }

    public void delete(int commentId, int profileId) {
        String sql = "DELETE FROM comment WHERE id = ? AND profile_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, commentId);
            stmt.setInt(2, profileId);

            stmt.executeUpdate();
            System.out.println("Comentário excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar comentário.", e);
        }
    }

    public List<Comment> listByReviewId(int reviewId) {
        String sql = """
            SELECT c.id, c.text, c.comment_date, c.profile_id, c.review_id, p.username
            FROM comment c
            INNER JOIN profile p ON c.profile_id = p.id
            WHERE c.review_id = ?
            ORDER BY c.comment_date ASC, c.id ASC
            """;
        List<Comment> comments = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setText(rs.getString("text"));
                    comment.setCommentDate(rs.getDate("comment_date").toLocalDate());
                    comment.setProfileId(rs.getInt("profile_id"));
                    comment.setReviewId(rs.getInt("review_id"));
                    comment.setUsername(rs.getString("username"));

                    comments.add(comment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar comentários.", e);
        }

        return comments;
    }
}