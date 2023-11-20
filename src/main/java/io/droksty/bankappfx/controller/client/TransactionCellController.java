package io.droksty.bankappfx.controller.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
    private Label payeeLabel;
    @FXML
    private Label amountLabel;
}
