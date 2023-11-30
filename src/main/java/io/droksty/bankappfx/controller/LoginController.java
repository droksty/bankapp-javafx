package io.droksty.bankappfx.controller;

import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


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
            if (Model.getInstance().authenticateAdmin(usernameField.getText(), passwordField.getText())) {
                ViewFactory.showAdminWindow();
                ViewFactory.closeStage((Stage) errorLabel.getScene().getWindow());
            } else {
                passwordField.setText("");
                errorLabel.setText("Wrong Credentials");
            }
        } else {
            if (Model.getInstance().authenticateClient(usernameField.getText(), passwordField.getText())) {
                Model.getInstance().setClient(usernameField.getText());
                ViewFactory.showClientWindow();
                ViewFactory.closeStage((Stage) errorLabel.getScene().getWindow());
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
