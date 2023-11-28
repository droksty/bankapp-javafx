package io.droksty.bankappfx.controller.admin;

import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.model.client.Client;
import io.droksty.bankappfx.view.ClientCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ClientListController {
    @FXML
    public ListView<Client> clientListView;

    @FXML
    private void initialize() {
        initializeData();
        clientListView.setItems(Model.getInstance().getClientList());
        clientListView.setCellFactory(e -> new ClientCellFactory());
    }

    private void initializeData() {
        if (Model.getInstance().getClientList().isEmpty()) {
            Model.getInstance().setClientList();
        }
    }
}
