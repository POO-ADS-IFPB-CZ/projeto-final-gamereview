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
    private void handleSalvarReview() {
        String titulo = txtGameTitle.getText();
        String plataforma = txtPlatform.getText();
        Double nota = cbRating.getValue();
        String conteudo = txtReviewContent.getText();

        if (titulo == null || titulo.isBlank() ||
                plataforma == null || plataforma.isBlank() ||
                nota == null || conteudo == null || conteudo.isBlank()) {

            exibirMensagem("Por favor, preencha todos os campos!", true);
            return;
        }

        int profileIdLogado = 1;

        Review novaReview = new Review(
                titulo,
                plataforma,
                nota,
                conteudo,
                LocalDate.now(),
                profileIdLogado
        );

        try {
            reviewDAO.salvar(novaReview);
            exibirMensagem("Review publicada com sucesso!", false);
            limparFormulario();
        } catch (Exception e) {
            exibirMensagem("Erro ao salvar review no banco de dados.", true);
        }
    }

    private void limparFormulario() {
        txtGameTitle.clear();
        txtPlatform.clear();
        cbRating.setValue(null);
        txtReviewContent.clear();
    }

    private void exibirMensagem(String msg, boolean isErro) {
        if (lblMessage != null) {
            lblMessage.setText(msg);
            lblMessage.setStyle(isErro ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        }
    }
}