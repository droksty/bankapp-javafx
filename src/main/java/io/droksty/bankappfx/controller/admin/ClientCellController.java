package io.droksty.bankappfx.controller.admin;

import io.droksty.bankappfx.model.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ClientCellController {
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Label userHandleLabel;
    @FXML
    private Label checkingAccNumLabel;
    @FXML
    private Label savingsAccNumLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @FXML
    private void initialize() {
        firstnameLabel.textProperty().bind(client.firstnameProperty());
        lastnameLabel.textProperty().bind(client.lastnameProperty());
        userHandleLabel.textProperty().bind(client.userHandleProperty());
        checkingAccNumLabel.textProperty().bind(client.checkingAccountProperty().asString());
        savingsAccNumLabel.textProperty().bind(client.savingsAccountProperty().asString());
        dateLabel.textProperty().bind(client.dateCreatedProperty().asString());
    }
}
