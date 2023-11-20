package io.droksty.bankappfx.controller.admin;

import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class AdminController {
    @FXML
    private BorderPane adminRoot;

    @FXML
    private void initialize() {
        ViewFactory.getAdminSelectionProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case CREATE_CLIENT -> adminRoot.setCenter(ViewFactory.getCreateClientPane());
                case CLIENT_LIST -> adminRoot.setCenter(ViewFactory.getClientListPane());
                case DEPOSIT -> adminRoot.setCenter(ViewFactory.getDepositPane());
            }
        });
    }
}
