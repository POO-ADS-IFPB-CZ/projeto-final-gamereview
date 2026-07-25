package com.gamereview.controller;

import com.gamereview.model.Review;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class ReviewCardController {

    @FXML private Label lblGameTitle;
    @FXML private Label lblRating;
    @FXML private Label lblPlatform;
    @FXML private Label lblDate;
    @FXML private Label lblText;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void setReviewData(Review review) {
        lblGameTitle.setText(review.getGameTitle());
        lblRating.setText(String.format("%.1f", review.getRating()));
        lblPlatform.setText(review.getPlatform().toUpperCase());

        if (review.getReviewDate() != null) {
            lblDate.setText(review.getReviewDate().format(DATE_FORMATTER));
        } else {
            lblDate.setText("");
        }

        lblText.setText(review.getText());
    }
}