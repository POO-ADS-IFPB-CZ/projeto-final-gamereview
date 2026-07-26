package com.gamereview.controller;

import com.gamereview.dao.ProfileDao;
import com.gamereview.model.Profile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public void register() throws IOException {

        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        Profile profile = new Profile();
        profile.setUsername(username);
        profile.setEmail(email);
        profile.setPassword(password);

        if (username.isBlank() || email.isBlank() || password.isBlank()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText("Fill in all fields.");
            alert.showAndWait();
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText("Enter a valid email address.");
            alert.showAndWait();
            return;
        }

        ProfileDao controller = new ProfileDao();

        boolean success = controller.createProfile(profile);

        if (success) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText("Registration completed");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(scene);

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText("Email already registered.");
            alert.showAndWait();
        }
    }

    @FXML
    public void toLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamereview/view/login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.setScene(scene);

    }
}
