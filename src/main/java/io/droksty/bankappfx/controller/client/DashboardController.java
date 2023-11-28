package io.droksty.bankappfx.controller.client;

import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.model.transaction.Transaction;
import io.droksty.bankappfx.view.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController {
    public Text userGreetingText;
    public Label dateLabel;
    public Label checkingAccBalance;
    public Label checkingAccDigits;
    public Label savingsAccBalance;
    public Label savingsAccDigits;
    public Label incomeLabel;
    public Label expensesLabel;
    public ListView<Transaction> transactionsListView;
    public TextField payeeField;
    public TextField amountField;
    public TextArea messageField;
    public Button sendButton;

    @FXML
    private void initialize() {
        bind();
        initializeLatestTransactions();
        transactionsListView.setItems(Model.getInstance().getLatestTransactions());
        transactionsListView.setCellFactory(e -> new TransactionCellFactory());
    }

    private void bind() {
        userGreetingText.textProperty().bind(Bindings.concat("Hi, ").concat(Model.getInstance().getClient().firstnameProperty()));
        dateLabel.setText("Today, " + LocalDate.now());
        checkingAccBalance.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        checkingAccDigits.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        savingsAccBalance.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
    }

    private void initializeLatestTransactions() {
        if (Model.getInstance().getLatestTransactions().isEmpty()) {
            Model.getInstance().setLatestTransactions();
        }
    }
}
