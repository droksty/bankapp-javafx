package io.droksty.bankappfx.view;

import io.droksty.bankappfx.controller.client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public final class ViewFactory {
    // Client View
    private static AnchorPane dashboardPane;
    private static AnchorPane transactionsPane;
    private static AnchorPane accountsPane;
    private static final ObjectProperty<ClientSidebarOptions> USER_SELECTION = new SimpleObjectProperty<>();

    // Admin View
    private static AnchorPane createClientPane;
    private static AnchorPane clientListPane;
    private static AnchorPane depositPane;
    private static final ObjectProperty<AdminSidebarOptions> ADMIN_SELECTION = new SimpleObjectProperty<>();

    // Private Constructor - Enforce noninstantiability
    private ViewFactory() {}

    // Public API - Getters
    public static ObjectProperty<ClientSidebarOptions> getUserSelectionProperty() {
        return USER_SELECTION;
    }
    public static ObjectProperty<AdminSidebarOptions> getAdminSelectionProperty() { return ADMIN_SELECTION; }

    // Public API - Client View
    public static AnchorPane getDashboardPane() {
        if (dashboardPane == null) {
            try {
                dashboardPane = new FXMLLoader(ViewFactory.class.getResource("/fxml/client/client-dashboard.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dashboardPane;
    }

    public static AnchorPane getTransactionsPane() {
        if (transactionsPane == null) {
            try {
                transactionsPane = new FXMLLoader(ViewFactory.class.getResource("/fxml/client/client-transactions.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return transactionsPane;
    }

    public static AnchorPane getAccountsPane() {
        if (accountsPane == null) {
            try {
                accountsPane = new FXMLLoader(ViewFactory.class.getResource("/fxml/client/client-accounts.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return accountsPane;
    }

    // Public API - Admin View
    public static AnchorPane getCreateClientPane() {
        if (createClientPane == null) {
            try {
                createClientPane = new FXMLLoader(ViewFactory.class.getResource("/fxml/admin/admin-create-client.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return createClientPane;
    }

    public static AnchorPane getClientListPane() {
        if (clientListPane == null) {
            try {
                clientListPane = new FXMLLoader(ViewFactory.class.getResource("/fxml/admin/admin-client-list.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return clientListPane;
    }

    public static AnchorPane getDepositPane() {
        if (depositPane == null) {
            try {
                depositPane = new FXMLLoader(ViewFactory.class.getResource("/fxml/admin/admin-deposit.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return depositPane;
    }

    // Public API - Stage Management
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

    public static void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(ViewFactory.class.getResource("/fxml/admin/admin.fxml"));
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
        stage.setResizable(false);
        stage.show();
    }
}
