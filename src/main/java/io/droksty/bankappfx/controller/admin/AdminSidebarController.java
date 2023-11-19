package io.droksty.bankappfx.controller.admin;

import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    private void onCreateNewClientButtonClick() {
        ViewFactory.getAdminSelectionProperty().set("Create");
    }
}
