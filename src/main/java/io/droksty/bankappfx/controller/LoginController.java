package io.droksty.bankappfx.controller;

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
        loginButton.setOnAction(event -> {
            if (adminLogin.selectedProperty().get()) {
                ViewFactory.showAdminWindow();
            } else {
                ViewFactory.showClientWindow();
            }
        });
    }
}
