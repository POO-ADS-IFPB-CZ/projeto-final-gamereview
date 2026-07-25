package com.gamereview.controller;

import com.gamereview.model.Profile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    public void toRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/register.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.setScene(scene);
    }
}
