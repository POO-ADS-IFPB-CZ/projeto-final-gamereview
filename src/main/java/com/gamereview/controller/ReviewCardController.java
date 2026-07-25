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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void setReviewData(Review review) {
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir esta review?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            ReviewDAO reviewDao = new ReviewDAO();
            reviewDao.delete(review.getId(), review.getProfileId());
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
            System.err.println("Erro ao abrir tela para editar review: " + e.getMessage());
            e.printStackTrace();
        }
    }
}