package com.gamereview.controller;

import com.gamereview.dao.ReviewDAO;
import com.gamereview.model.Profile;
import com.gamereview.model.Review;
import com.gamereview.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ReviewCardController {

    @FXML private Label lblGameTitle;
    @FXML private Label lblRating;
    @FXML private Label lblPlatform;
    @FXML private Label lblDate;
    @FXML private Label lblText;
    @FXML private Label lblUser;
    @FXML private Button btnDel;
    @FXML private Button btnUpd;
    @FXML private Button btnComments;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Review currentReview;

    public void setReviewData(Review review) {
        this.currentReview = review;

        Profile user = UserSession.getInstance().getUser();
        lblGameTitle.setText(review.getGameTitle());
        lblRating.setText(String.format("%.1f", review.getRating()));
        lblPlatform.setText(review.getPlatform().toUpperCase());

        if (review.getReviewDate() != null) {
            lblDate.setText(review.getReviewDate().format(DATE_FORMATTER));
        } else {
            lblDate.setText("");
        }

        lblText.setText(review.getText());

        lblUser.setText(review.getUsername());

        if (user != null && user.getId() == review.getProfileId()) {
            btnDel.setVisible(true);
            btnDel.setOnAction(event -> handleDeleteReview(review));

            btnUpd.setVisible(true);
            btnUpd.setOnAction(event -> handleEditReview(review));
        } else {
            btnDel.setVisible(false);
            btnUpd.setVisible(false);
        }
    }

    public void handleDeleteReview(Review review) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to delete this review?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            ReviewDAO reviewDao = new ReviewDAO();
            reviewDao.delete(review.getId(), review.getProfileId());

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

    @FXML
    private void handleEditReview(Review review) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/reviews/createReview.fxml"));
            Parent root = loader.load();

            ReviewController controller = loader.getController();
            controller.setReviewToEdit(review);

            Stage stage = (Stage) lblGameTitle.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Error opening screen to edit review: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOpenComments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/reviews/reviewDetails.fxml"));
            Parent root = loader.load();

            ReviewDetailsController controller = loader.getController();
            controller.setReviewData(currentReview);

            Stage stage = (Stage) lblGameTitle.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao abrir comentários: " + e.getMessage());
            e.printStackTrace();
        }
    }
}