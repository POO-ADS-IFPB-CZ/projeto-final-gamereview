package com.gamereview.controller;

import com.gamereview.model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CommentCardController {

    @FXML private Label lblUser;
    @FXML private Label lblText;

    public void setCommentData(Comment comment) {
        lblUser.setText("@" + comment.getUsername());
        lblText.setText(comment.getText());
    }
}