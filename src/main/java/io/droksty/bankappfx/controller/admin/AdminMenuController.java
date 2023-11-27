package io.droksty.bankappfx.controller.admin;

import io.droksty.bankappfx.view.AdminSidebarOptions;
import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminMenuController {
    @FXML
    private Button createNewClientButton;
    @FXML
    private Button clientsButton;
    @FXML
    private Button depositButton;
    @FXML
    private Button logoutButton;

    @FXML
    private void initialize() {
        logoutButton.setOnAction(event -> onLogout());
    }

    @FXML
    private void onCreateNewClientButtonClick() {
        ViewFactory.getAdminSelectionProperty().set(AdminSidebarOptions.CREATE_CLIENT);
    }

    @FXML
    private void onClientListButtonClick() {
        ViewFactory.getAdminSelectionProperty().set(AdminSidebarOptions.CLIENT_LIST);
    }

    @FXML
    private void onDepositButtonClick() {
        ViewFactory.getAdminSelectionProperty().set(AdminSidebarOptions.DEPOSIT);
    }

    private void onLogout() {
        ViewFactory.showLoginWindow();
        ViewFactory.closeStage((Stage) depositButton.getScene().getWindow());
    }
}
