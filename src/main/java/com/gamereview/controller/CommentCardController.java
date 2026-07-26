package com.gamereview.controller;

import com.gamereview.model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class CommentCardController {

    @FXML private Label lblUser;
    @FXML private Label lblText;
    @FXML private Label lblDate;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void setCommentData(Comment comment) {
        lblUser.setText("@" + comment.getUsername());
        lblText.setText(comment.getText());

        if (comment.getCommentDate() != null) {
            lblDate.setText(comment.getCommentDate().format(DATE_FORMATTER));
        }
    }
}