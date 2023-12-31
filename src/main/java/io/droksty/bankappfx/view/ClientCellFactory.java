package io.droksty.bankappfx.view;

import io.droksty.bankappfx.controller.admin.ClientCellController;
import io.droksty.bankappfx.model.client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ClientCellFactory extends ListCell<Client> {
    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/ClientCell.fxml"));
            ClientCellController clientCellController = new ClientCellController(client);
            loader.setController(clientCellController);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
