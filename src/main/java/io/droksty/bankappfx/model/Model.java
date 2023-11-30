package io.droksty.bankappfx.model;

import io.droksty.bankappfx.model.account.CheckingAccount;
import io.droksty.bankappfx.model.account.SavingsAccount;
import io.droksty.bankappfx.model.client.Client;
import io.droksty.bankappfx.model.transaction.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Model {
    private static Model model;
    private final DatabaseDriver databaseDriver;
    private final Client client;
    private final ObservableList<Client> clientList;
    private final ObservableList<Transaction> latestTransactions;
    private final ObservableList<Transaction> allTransactions;

    private Model() {
        this.databaseDriver = new DatabaseDriver();
        this.client = new Client();
        this.clientList = FXCollections.observableArrayList();
        this.latestTransactions = FXCollections.observableArrayList();
        this.allTransactions = FXCollections.observableArrayList();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }


    /*                                                  */
    /*                                                  */
    /*                                                  */

    public Client getClient() {
        return client;
    }

    public void setClient(String userHandle) {
        Client client = databaseDriver.getOneClient(userHandle);
        this.client.firstnameProperty().set(client.firstnameProperty().getValue());
        this.client.lastnameProperty().set(client.lastnameProperty().getValue());
        this.client.userHandleProperty().set(client.userHandleProperty().getValue());
        this.client.dateCreatedProperty().set(client.dateCreatedProperty().getValue());
        CheckingAccount checkingAccount = databaseDriver.getCheckingAccount(userHandle);
        SavingsAccount savingsAccount = databaseDriver.getSavingsAccount(userHandle);
        this.client.checkingAccountProperty().set(checkingAccount);
        this.client.savingsAccountProperty().set(savingsAccount);
    }

    public ObservableList<Client> getClientList() {
        return clientList;
    }

    public void setClientList() {
        List<Client> list = databaseDriver.getAllClients();
        clientList.addAll(list);
    }


    // Service Layer ?

    public boolean authenticateClient(String userHandle, String password) {
        return databaseDriver.clientExists(userHandle, password);
    }

    public boolean authenticateAdmin(String username, String password) {
        return databaseDriver.adminExists(username, password);
    }

    public Client searchOne(String userHandle) {
        return databaseDriver.getOneClient(userHandle);
    }

    private void prepareTransactions(ObservableList<Transaction> transactions, int limit) {
        List<Transaction> transactionList = databaseDriver.getTransactions(this.client.userHandleProperty().get(), limit);
        transactions.addAll(transactionList);
    }

    public void setLatestTransactions() {
        prepareTransactions(this.latestTransactions, 4);
    }

    public ObservableList<Transaction> getLatestTransactions() {
        return latestTransactions;
    }

    public void setAllTransactions() {
        prepareTransactions(this.allTransactions, -1);
    }

    public ObservableList<Transaction> getAllTransactions() {
        return allTransactions;
    }
}
