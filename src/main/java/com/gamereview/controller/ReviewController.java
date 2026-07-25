package com.gamereview.controller;

import com.gamereview.dao.ReviewDAO;
import com.gamereview.model.Review;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ReviewController {

    @FXML private TextField txtGameTitle;
    @FXML private TextField txtPlatform;
    @FXML private ComboBox<Double> cbRating;
    @FXML private TextArea txtReviewContent;
    @FXML private Label lblMessage;

    private final ReviewDAO reviewDAO = new ReviewDAO();

    @FXML
    public void initialize() {
        if (cbRating != null) {
            cbRating.getItems().addAll(1.0, 2.0, 3.0, 4.0, 5.0);
        }
    }

    @FXML
    private void handleSaveReview() {
        String title = txtGameTitle.getText();
        String platform = txtPlatform.getText();
        Double rating = cbRating.getValue();
        String content = txtReviewContent.getText();

        if (title == null || title.isBlank() ||
                platform == null || platform.isBlank() ||
                rating == null || content == null || content.isBlank()) {

            showMessage("Por favor, preencha todos os campos!", true);
            return;
        }

        int profileIdload = 1;

        Review newReview = new Review(
                title,
                platform,
                rating,
                content,
                LocalDate.now(),
                profileIdload
        );

        try {
            reviewDAO.save(newReview);
            showMessage("Review publicada com sucesso!", false);
            clearForms();
        } catch (Exception e) {
            showMessage("Erro ao salvar review no banco de dados.", true);
        }
    }

    private void clearForms() {
        txtGameTitle.clear();
        txtPlatform.clear();
        cbRating.setValue(null);
        txtReviewContent.clear();
    }

    private void showMessage(String msg, boolean isErro) {
        if (lblMessage != null) {
            lblMessage.setText(msg);
            lblMessage.setStyle(isErro ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        }
    }
}