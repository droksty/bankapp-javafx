package io.droksty.bankappfx.controller.client;

import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.model.transaction.Transaction;
import io.droksty.bankappfx.view.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDate;

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
    public TextField receiverField;
    public TextField amountField;
    public TextArea messageField;
    public Button sendButton;

    @FXML
    private void initialize() {
        bind();
        initializeLatestTransactions();
        transactionsListView.setItems(Model.getInstance().getLatestTransactions());
        transactionsListView.setCellFactory(e -> new TransactionCellFactory());
        sendButton.setOnAction(event -> onSendMoney());
        calculateAccountSummary();
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

    private void onSendMoney() {
        String receiver = receiverField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String message = messageField.getText();
        String sender = Model.getInstance().getClient().userHandleProperty().get();

        Model.getInstance().getDatabaseDriver().updateSavingsAccountBalance(receiver, amount);
        Model.getInstance().getDatabaseDriver().updateSavingsAccountBalance(sender, -amount);

        double newBalance = Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(sender);
        Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().set(newBalance);

        Model.getInstance().getDatabaseDriver().insertTransaction(sender, receiver, amount, message);

        receiverField.setText("");
        amountField.setText("");
        messageField.setText("");
    }


    private void calculateAccountSummary() {
        double income = 0, expenses = 0;
        if (Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
        for (Transaction transaction : Model.getInstance().getAllTransactions()) {
            if (transaction.senderProperty().get().equals(Model.getInstance().getClient().userHandleProperty().get())) {
                expenses += transaction.amountProperty().get();
            } else {
                income += transaction.amountProperty().get();
            }
        }
        incomeLabel.setText("+ €" + income);
        expensesLabel.setText("+ €" + expenses);
    }
}
