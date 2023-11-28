package io.droksty.bankappfx.controller.admin;

import io.droksty.bankappfx.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Random;

public class CreateClientController {
    @FXML private TextField firstnameField;
    @FXML private TextField lastnameField;
    @FXML private CheckBox generateUserHandleCheckBox;
    @FXML private Label userHandleLabel;
    @FXML private TextField passwordField;
    @FXML private TextField checkingBalanceField;
    @FXML private TextField savingsBalanceField;
    @FXML private Button createClientButton;
    @FXML private Label messageLabel;

    private String userHandle;


    @FXML
    private void initialize() {
        createClientButton.setOnAction(event -> createClient());
        generateUserHandleCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                userHandle = createUserHandle();
                onCreateUserHandle();
            }
        });
    }


    private void createClient() {
        createAccount("checking");
        createAccount("savings");

        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String password = passwordField.getText();
        Model.getInstance().getDatabaseDriver().createClient(firstname, lastname, userHandle, password, LocalDate.now());

        messageLabel.setText("Client created");
        clearForm();
    }

    private void createAccount(String accountType) {
        String firstSection = "3201";
        String lastSection = Integer.toString((new Random()).nextInt(9999) + 1000);
        String accountNum = firstSection + " " + lastSection;
        if (accountType.equals("checking")) {
            double balance = Double.parseDouble(checkingBalanceField.getText());
            Model.getInstance().getDatabaseDriver().createCheckingAccount(userHandle, accountNum, 10, balance);
        } else {
            double balance = Double.parseDouble(savingsBalanceField.getText());
            Model.getInstance().getDatabaseDriver().createSavingsAccount(userHandle, accountNum, 2000, balance);
        }
    }

    private void clearForm() {
        firstnameField.setText("");
        lastnameField.setText("");
        generateUserHandleCheckBox.setSelected(false);
        userHandleLabel.setText("");
        passwordField.setText("");
        checkingBalanceField.setText("");
        savingsBalanceField.setText("");
    }

    private void onCreateUserHandle() {
        if (firstnameField.getText() != null && lastnameField.getText() != null) {
            userHandleLabel.setText(userHandle);
        }
    }

    private String createUserHandle() {
        int id = Model.getInstance().getDatabaseDriver().getLastClientID() + 1;
        char fChar = Character.toLowerCase(firstnameField.getText().charAt(0));
        return "@" + fChar + lastnameField.getText() + id;
    }
}
