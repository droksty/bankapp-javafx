package io.droksty.bankappfx.controller.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.droksty.bankappfx.model.Model;
import io.droksty.bankappfx.model.transaction.Transaction;
import io.droksty.bankappfx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TransactionCellController {
    @FXML
    private FontAwesomeIconView incomingIcon;
    @FXML
    private FontAwesomeIconView outcomingIcon;
    @FXML
    private Label datetimeLabel;
    @FXML
    private Label senderLabel;
    @FXML
    private Label receiverLabel;
    @FXML
    private Button messageButton;
    @FXML
    private Label amountLabel;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @FXML
    private void initialize() {
        senderLabel.textProperty().bind(transaction.senderProperty());
        receiverLabel.textProperty().bind(transaction.receiverProperty());
        amountLabel.textProperty().bind(transaction.amountProperty().asString());
        datetimeLabel.textProperty().bind(transaction.dateProperty().asString());
        setTransactionIcons();
        messageButton.setOnAction(event -> ViewFactory.showMessageWindow(transaction.senderProperty().get(), transaction.messageProperty().get()));
    }

    private void setTransactionIcons() {
        if (transaction.senderProperty().get().equals(Model.getInstance().getClient().userHandleProperty().get())) {
            incomingIcon.setVisible(false);
            outcomingIcon.setFill(Color.RED);
        } else {
            outcomingIcon.setVisible(false);
            incomingIcon.setFill(Color.GREEN);
        }
    }
}
