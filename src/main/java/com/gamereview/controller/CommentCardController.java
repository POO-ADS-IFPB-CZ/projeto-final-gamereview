package com.gamereview.controller;

import com.gamereview.dao.CommentDAO;
import com.gamereview.model.Comment;
import com.gamereview.util.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.time.format.DateTimeFormatter;

public class CommentCardController {

    @FXML private Label lblUser;
    @FXML private Label lblText;
    @FXML private Label lblDate;
    @FXML private TextArea txtEditComment;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnSaveEdit;
    @FXML private Button btnCancelEdit;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final CommentDAO commentDAO = new CommentDAO();

    private Comment comment;
    private Runnable onChanged;

    public void setCommentData(Comment comment, int reviewOwnerId, Runnable onChanged) {
        this.comment = comment;
        this.onChanged = onChanged;

        lblUser.setText("@" + comment.getUsername());
        lblText.setText(comment.getText());

        if (comment.getCommentDate() != null) {
            lblDate.setText(comment.getCommentDate().format(DATE_FORMATTER));
        }

        var currentUser = UserSession.getInstance().getUser();
        boolean isCommentOwner = currentUser != null && currentUser.getId() == comment.getProfileId();
        boolean isReviewOwner = currentUser != null && currentUser.getId() == reviewOwnerId;

        btnEdit.setVisible(isCommentOwner);
        btnEdit.setManaged(isCommentOwner);

        boolean canDelete = isCommentOwner || isReviewOwner;
        btnDelete.setVisible(canDelete);
        btnDelete.setManaged(canDelete);
    }

    @FXML
    private void handleEdit() {
        txtEditComment.setText(comment.getText());

        lblText.setVisible(false);
        lblText.setManaged(false);
        txtEditComment.setVisible(true);
        txtEditComment.setManaged(true);

        btnEdit.setVisible(false);
        btnEdit.setManaged(false);
        btnDelete.setVisible(false);
        btnDelete.setManaged(false);

        btnSaveEdit.setVisible(true);
        btnSaveEdit.setManaged(true);
        btnCancelEdit.setVisible(true);
        btnCancelEdit.setManaged(true);
    }

    @FXML
    private void handleCancelEdit() {
        lblText.setVisible(true);
        lblText.setManaged(true);
        txtEditComment.setVisible(false);
        txtEditComment.setManaged(false);

        btnEdit.setVisible(true);
        btnEdit.setManaged(true);
        btnDelete.setVisible(true);
        btnDelete.setManaged(true);

        btnSaveEdit.setVisible(false);
        btnSaveEdit.setManaged(false);
        btnCancelEdit.setVisible(false);
        btnCancelEdit.setManaged(false);
    }

    @FXML
    private void handleSaveEdit() {
        String newText = txtEditComment.getText();

        if (newText == null || newText.isBlank()) {
            return;
        }

        commentDAO.update(comment.getId(), comment.getProfileId(), newText.trim());
        onChanged.run();
    }

    @FXML
    private void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Deseja realmente excluir este comentário?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            commentDAO.delete(comment.getId());
            onChanged.run();
        }
    }
}