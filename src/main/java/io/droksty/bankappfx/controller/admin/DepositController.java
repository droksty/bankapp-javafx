package io.droksty.bankappfx.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DepositController {
    @FXML
    private TextField payeeHandleField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView resultListView;
    @FXML
    private TextField amountField;
    @FXML
    private Button depositButton;
}
