package com.gamereview.controller;

import com.gamereview.model.Profile;
import com.gamereview.util.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.gamereview.dao.ProfileDao;

import java.io.IOException;

public class UserProfileController {

    private final ProfileDao profileDao = new ProfileDao();

    @FXML
    private VBox profileView;


    @FXML
    private VBox profileEdit;


    @FXML
    private Label lblName;


    @FXML
    private Label lblEmail;


    @FXML
    private TextField txtName;


    @FXML
    private TextField txtEmail;


    @FXML
    private PasswordField txtPassword;

    @FXML
    public void editProfile(){

        profileView.setVisible(false);
        profileView.setManaged(false);


        profileEdit.setVisible(true);
        profileEdit.setManaged(true);

    }

    @FXML
    public void deleteProfile() {
        Profile profile = UserSession.getInstance().getUser();
        profileDao.delete(profile.getId());

        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/gamereview/view/login.fxml")
            );
            Stage stage =
                    (Stage) profileEdit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void back() {
        profileEdit.setVisible(false);
        profileEdit.setManaged(false);
        profileView.setVisible(true);
        profileView.setManaged(true);

    }

    @FXML
    public void initialize(){

        Profile profile = UserSession.getInstance().getUser();

        lblName.setText(profile.getUsername());

        lblEmail.setText(profile.getEmail());


        txtName.setText(profile.getUsername());

        txtEmail.setText(profile.getEmail());

    }

    @FXML
    public void saveProfile(){

        Profile profile = UserSession.getInstance().getUser();

        profile.setUsername(txtName.getText());
        profile.setEmail(txtEmail.getText());

        if(!txtPassword.getText().isBlank()){
            profile.setPassword(txtPassword.getText());
        }

        boolean updated = profileDao.update(profile);

        if(updated){
            lblName.setText(profile.getUsername());
            lblEmail.setText(profile.getEmail());
            back();
        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Profile");
            alert.setHeaderText(null);
            alert.setContentText("This email is already being used.");
            alert.showAndWait();

        }
    }

    @FXML
    public void Home(){

        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/gamereview/view/home.fxml")
            );
            Stage stage = (Stage) profileView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
