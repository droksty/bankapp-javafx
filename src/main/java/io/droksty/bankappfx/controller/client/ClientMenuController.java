package io.droksty.bankappfx.controller.client;

import io.droksty.bankappfx.view.ClientSidebarOptions;
import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClientMenuController {
    @FXML
    private Button dashboardButton;
    @FXML
    private Button transactionsButton;
    @FXML
    private Button accountsButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;

    @FXML
    private void initialize() {
        logoutButton.setOnAction(event -> onLogout());
    }

    @FXML
    private void onDashboardButtonClick() {
        ViewFactory.getUserSelectionProperty().set(ClientSidebarOptions.DASHBOARD);
    }

    @FXML
    private void onTransactionsButtonClick() {
        ViewFactory.getUserSelectionProperty().set(ClientSidebarOptions.TRANSACTIONS);
    }

    @FXML
    private void onAccountButtonClick() {
        ViewFactory.getUserSelectionProperty().set(ClientSidebarOptions.ACCOUNTS);
    }

    private void onLogout() {
        ViewFactory.showLoginWindow();
        ViewFactory.closeStage((Stage) dashboardButton.getScene().getWindow());
    }
}
