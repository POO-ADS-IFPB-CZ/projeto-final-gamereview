package com.gamereview.controller;

import com.gamereview.model.Profile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

        if (email.isBlank() || password.isBlank()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Fill in all fields.");
            alert.showAndWait();
            return;
        }

        ProfileController profileController = new ProfileController();

        Profile profile = profileController.login(email, password);

        if (profile != null) {
            System.out.println("1");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password.");
            alert.showAndWait();
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
