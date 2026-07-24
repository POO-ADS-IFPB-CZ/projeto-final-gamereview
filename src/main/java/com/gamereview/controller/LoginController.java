package com.gamereview.controller;

import com.gamereview.model.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public void login() {

        String email = txtEmail.getText();
        String password = txtPassword.getText();

        ProfileController profileController = new ProfileController();

        Profile profile = profileController.login(email, password);

        if (profile != null) {
            System.out.println("1");
        } else {
            System.out.println("0");
        }
    }
}
