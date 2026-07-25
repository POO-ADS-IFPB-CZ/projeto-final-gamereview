package com.gamereview.controller;

import com.gamereview.dao.ReviewDAO;
import com.gamereview.model.Profile;
import com.gamereview.model.Review;
import com.gamereview.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HomeController {

    @FXML private VBox vboxReviewsContainer;

    private final ReviewDAO reviewDAO = new ReviewDAO();

    @FXML
    public void initialize() {
        System.out.println("Ola " + UserSession.getInstance().getUser());
        loadReviews(reviewDAO.listReviews());
    }

    private void loadReviews(List<Review> reviews) {
        vboxReviewsContainer.getChildren().clear();

        if (reviews.isEmpty()) {
            Label lblVazio = new Label("Nenhuma review encontrada.");
            lblVazio.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");
            vboxReviewsContainer.getChildren().add(lblVazio);
            return;
        }

        for (Review review : reviews) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/reviews/cardReview.fxml"));
                Node cardNode = loader.load();

                ReviewCardController cardController = loader.getController();
                if (cardController != null) {
                    cardController.setReviewData(review);
                }

                vboxReviewsContainer.getChildren().add(cardNode);

            } catch (IOException e) {
                System.err.println("Erro ao carregar cardReview.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCreateReview() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/reviews/createReview.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) vboxReviewsContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao abrir a tela de criação de review!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gamereview/view/login.fxml"));
            Stage stage = (Stage) vboxReviewsContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}