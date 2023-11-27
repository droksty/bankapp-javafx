package io.droksty.bankappfx.controller;

import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class LoginController {
    @FXML
    private RadioButton adminLogin;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        adminLogin.selectedProperty().addListener(observable -> changeLabel());
        loginButton.setOnAction(event -> login());
    }

    private void login() {
        if (adminLogin.selectedProperty().get()) {
            if (Model.getInstance().isAdminLoginAuthorised(usernameField.getText(), passwordField.getText())) {
                ViewFactory.showAdminWindow();
                // Close Login Stage?
            } else {
                passwordField.setText("");
                errorLabel.setText("Wrong Credentials");
            }
        } else {
            if (Model.getInstance().isClientLoginAuthorised(usernameField.getText(), passwordField.getText())) {
                ViewFactory.showClientWindow();
                // Close Login Stage?
            } else {
                passwordField.setText("");
                errorLabel.setText("Wrong Credentials");
            }
        }
    }

    private void changeLabel() {
        if (adminLogin.selectedProperty().get()) {
            usernameLabel.setText("Username");
        } else {
            usernameLabel.setText("User Handle");
        }
    }
}
