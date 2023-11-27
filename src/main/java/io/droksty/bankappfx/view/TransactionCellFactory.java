package io.droksty.bankappfx.view;

import io.droksty.bankappfx.controller.client.TransactionCellController;
import io.droksty.bankappfx.model.transaction.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class TransactionCellFactory extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/TransactionCell.fxml"));
            TransactionCellController transactionCellController = new TransactionCellController(transaction);
            loader.setController(transactionCellController);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
