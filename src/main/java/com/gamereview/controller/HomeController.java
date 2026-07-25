package com.gamereview.controller;

import com.gamereview.dao.ReviewDAO;
import com.gamereview.model.Review;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class HomeController {

    @FXML private VBox vboxReviewsContainer;

    private final ReviewDAO reviewDAO = new ReviewDAO();

    @FXML
    public void initialize() {
        loadReviews();
    }

    private void loadReviews() {
        vboxReviewsContainer.getChildren().clear();

        try {
            List<Review> reviews = reviewDAO.listReviews();

            if (reviews.isEmpty()) {
                Label lblVazio = new Label("Nenhuma review encontrada.");
                lblVazio.setStyle("-fx-font-size: 14px; -fx-text-fill: #777777;");
                vboxReviewsContainer.getChildren().add(lblVazio);
                return;
            }

            for (Review review : reviews) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/reviews/cardReview.fxml"));
                Node cardNode = loader.load();

                ReviewCardController cardController = loader.getController();
                cardController.setReviewData(review);

                vboxReviewsContainer.getChildren().add(cardNode);
            }

        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}