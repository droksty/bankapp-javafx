package io.droksty.bankappfx.controller.client;

import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class ClientController {
    @FXML
    private BorderPane clientRoot;

    @FXML
    private void initialize() {
        ViewFactory.getUserSelectionProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case DASHBOARD -> clientRoot.setCenter(ViewFactory.getDashboardPane());
                case TRANSACTIONS -> clientRoot.setCenter(ViewFactory.getTransactionsPane());
                case ACCOUNTS -> clientRoot.setCenter(ViewFactory.getAccountsPane());
            }
        });
    }
}
