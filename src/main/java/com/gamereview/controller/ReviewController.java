package com.gamereview.controller;

import com.gamereview.dao.ReviewDAO;
import com.gamereview.model.Profile;
import com.gamereview.model.Review;
import com.gamereview.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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
        Profile user = UserSession.getInstance().getUser();
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

        if (reviewToEdit == null) {
            Review newReview = new Review(title, platform, rating, content, LocalDate.now(), user.getId());
            reviewDAO.save(newReview);
            showMessage("Review criada com sucesso!", false);
        } else {
            reviewToEdit.setGameTitle(title);
            reviewToEdit.setPlatform(platform);
            reviewToEdit.setRating(rating);
            reviewToEdit.setText(content);

            reviewDAO.update(reviewToEdit);
            showMessage("Review atualizada com sucesso!", false);
        }

        clearForms();
        handleBack();
    }

    private Review reviewToEdit = null;

    public void setReviewToEdit(Review review) {
        this.reviewToEdit = review;
        txtGameTitle.setText(review.getGameTitle());
        txtPlatform.setText(review.getPlatform());
        cbRating.setValue(review.getRating());
        txtReviewContent.setText(review.getText());

        lblMessage.setText("Editando review de " + review.getGameTitle());
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gamereview/view/home.fxml"));
            Stage stage = (Stage) txtGameTitle.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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