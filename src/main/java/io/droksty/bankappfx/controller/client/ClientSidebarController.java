package io.droksty.bankappfx.controller.client;

import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ClientSidebarController {
    @FXML
    private Button dashboardButton;
    @FXML
    private Button transactionsButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;


    @FXML
    private void onDashboardButtonClick() {
        ViewFactory.getUserSelectionProperty().set("Dashboard");
    }

    @FXML
    private void onTransactionsButtonClick() {
        ViewFactory.getUserSelectionProperty().set("Transactions");
    }

    @FXML
    private void onAccountButtonClick() {
        ViewFactory.getUserSelectionProperty().set("Accounts");
    }
}
