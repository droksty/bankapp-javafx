package io.droksty.bankappfx.controller;

import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class ClientController {
    @FXML
    private BorderPane clientParentNode;

    @FXML
    private void initialize() {
        ViewFactory.getUserSelectionProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Transactions" -> clientParentNode.setCenter(ViewFactory.getTransactions());
                case "Accounts" -> clientParentNode.setCenter(ViewFactory.getAccounts());
                default -> clientParentNode.setCenter(ViewFactory.getDashboard());
            }
        });
    }
}
