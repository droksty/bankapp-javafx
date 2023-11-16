package io.droksty.bankappfx.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientDashboardController implements Initializable {
    public Text userGreetingText;
    public Label dateLabel;
    public Label checkingAccountBalance;
    public Label checkingAccountDigits;
    public Label savingsAccountBalance;
    public Label savingsAccountDigits;
    public Label incomeLabel;
    public Label expensesLabel;
    public ListView transactionsListView;
    public TextField payeeField;
    public TextField amountField;
    public TextArea messageField;
    public Button sendButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
