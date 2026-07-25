package com.gamereview.controller;

import com.gamereview.model.Profile;
import com.gamereview.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private Button btnEntrar;

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
            try {
                UserSession.initSession(profile);
                Parent root = FXMLLoader.load(getClass().getResource("/com/gamereview/view/home.fxml"));

                Stage stage = (Stage) btnEntrar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("0");
        }
    }
}
