package io.droksty.bankappfx.controller.admin;

import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.model.client.Client;
import io.droksty.bankappfx.view.ClientCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DepositController {
    @FXML
    private TextField userHandleField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<Client> resultListView;
    @FXML
    private TextField amountField;
    @FXML
    private Button depositButton;

    private Client client;

    @FXML
    private void initialize() {
        searchButton.setOnAction(event -> onSearchClient());
        depositButton.setOnAction(event -> onDeposit());
    }

    private void onSearchClient() {
        Client client = Model.getInstance().searchOne(userHandleField.getText());
        ObservableList<Client> list = FXCollections.observableArrayList(client);
        resultListView.setItems(list);
        resultListView.setCellFactory(e -> new ClientCellFactory());
        this.client = client;
    }

    private void onDeposit() {
        double amount = Double.parseDouble(amountField.getText());
//        double newBalance = amount + client.savingsAccountProperty().get().balanceProperty().get();
        if (amountField.getText() != null) {
            Model.getInstance().getDatabaseDriver().updateSavingsAccountBalance(client.userHandleProperty().get(), amount);
        }
        clearFields();
    }

    private void clearFields() {
        userHandleField.setText("");
        amountField.setText("");
    }
}
