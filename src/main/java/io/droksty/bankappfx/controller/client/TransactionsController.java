package io.droksty.bankappfx.controller.client;

import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.model.transaction.Transaction;
import io.droksty.bankappfx.view.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView<Transaction> transactionsListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTransactionList();
        transactionsListView.setItems(Model.getInstance().getAllTransactions());
        transactionsListView.setCellFactory(e -> new TransactionCellFactory());
    }

    private void initTransactionList() {
        if (Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
    }
}
