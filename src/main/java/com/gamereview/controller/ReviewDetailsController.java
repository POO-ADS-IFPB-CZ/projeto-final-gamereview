package com.gamereview.controller;

import com.gamereview.dao.CommentDAO;
import com.gamereview.model.Comment;
import com.gamereview.model.Review;
import com.gamereview.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReviewDetailsController {

    @FXML private Label lblGameTitle;
    @FXML private Label lblText;
    @FXML private VBox vboxCommentsContainer;
    @FXML private TextArea txtNewComment;

    private final CommentDAO commentDAO = new CommentDAO();
    private Review review;

    public void setReviewData(Review review) {
        this.review = review;
        lblGameTitle.setText(review.getGameTitle());
        lblText.setText(review.getText());
        loadComments();
    }

    private void loadComments() {
        vboxCommentsContainer.getChildren().clear();

        List<Comment> comments = commentDAO.listByReviewId(review.getId());

        for (Comment comment : comments) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/reviews/commentCard.fxml"));
                Node cardNode = loader.load();

                CommentCardController cardController = loader.getController();
                cardController.setCommentData(comment, review.getProfileId(), this::loadComments);

                vboxCommentsContainer.getChildren().add(cardNode);

            } catch (IOException e) {
                System.err.println("Erro ao carregar commentCard.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handlePublishComment() {
        String text = txtNewComment.getText();
        if (text == null || text.isBlank()) {
            return;
        }

        Comment comment = new Comment(
                text.trim(),
                LocalDate.now(),
                UserSession.getInstance().getUser().getId(),
                review.getId()
        );

        commentDAO.save(comment);
        txtNewComment.clear();
        loadComments();
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gamereview/view/home.fxml"));
            Stage stage = (Stage) lblGameTitle.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}