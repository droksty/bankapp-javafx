package io.droksty.bankappfx.view;

import io.droksty.bankappfx.controller.client.ClientController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public final class ViewFactory {
    // Client View
    private static AnchorPane dashboard;
    private static AnchorPane transactions;
    private static AnchorPane accounts;
    private static final StringProperty userSelection = new SimpleStringProperty("");

    // Private Constructor - Enforce noninstantiability
    private ViewFactory() {}


    // Public API
    public static StringProperty getUserSelectionProperty() {
        return userSelection;
    }


    public static AnchorPane getDashboard () {
        if (dashboard == null) {
            try {
                dashboard = new FXMLLoader(ViewFactory.class.getResource("/fxml/client/client-dashboard.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dashboard;
    }

    public static AnchorPane getTransactions() {
        if (transactions == null) {
            try {
                transactions = new FXMLLoader(ViewFactory.class.getResource("/fxml/client/client-transactions.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }

    public static AnchorPane getAccounts() {
        if (accounts == null) {
            try {
                accounts = new FXMLLoader(ViewFactory.class.getResource("/fxml/client/client-accounts.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return accounts;
    }

    public static void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(ViewFactory.class.getResource("/fxml/login.fxml"));
        createStage(loader);
    }

    public static void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(ViewFactory.class.getResource("/fxml/client/client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }


    // Helper methods
    private static void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BankAppFX");
        stage.show();
    }
}
