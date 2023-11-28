package io.droksty.bankappfx.controller.admin;

import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.model.client.Client;
import io.droksty.bankappfx.view.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DepositController {
    @FXML
    private TextField payeeHandleField;
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
        searchButton.setOnAction(event -> onClientSearch());
        depositButton.setOnAction(event -> deposit());
    }

    private void onClientSearch() {
        ObservableList<Client> searchResult = Model.getInstance().searchOne(payeeHandleField.getText());
        resultListView.setItems(searchResult);
        resultListView.setCellFactory(e -> new ClientCellFactory());
        client = searchResult.get(0);
    }

    private void deposit() {
        double amount = Double.parseDouble(amountField.getText());
        double newBalance = amount + client.savingsAccountProperty().get().balanceProperty().get();
        if (amountField.getText() != null) {
            Model.getInstance().getDatabaseDriver().depositSavings(client.userHandleProperty().get(), newBalance);
        }
        clearFields();
    }

    private void clearFields() {
        payeeHandleField.setText("");
        amountField.setText("");
    }
}
