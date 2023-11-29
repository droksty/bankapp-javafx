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
    private final ObservableList<Transaction> latestTransactions;
    private final ObservableList<Transaction> allTransactions;
    private final ObservableList<Client> clientList;

    private Model() {
        this.databaseDriver = new DatabaseDriver();
        this.client = new Client("", "", "", null, null, null);
        this.latestTransactions = FXCollections.observableArrayList();
        this.allTransactions = FXCollections.observableArrayList();
        this.clientList = FXCollections.observableArrayList();
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














    /*
    * Client Section
    */

    public Client getClient() {
        return client;
    }

    public boolean isClientLoginAuthorised(String userHandle, String password) {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet rs = databaseDriver.getClientData(userHandle, password);
        try {
            if (rs.isBeforeFirst()) {
                this.client.firstnameProperty().set(rs.getString("firstname"));
                this.client.lastnameProperty().set(rs.getString("lastname"));
                this.client.userHandleProperty().set(rs.getString("user_handle"));
                this.client.dateCreatedProperty().set(getDateFromResultSet(rs.getString("date")));
                checkingAccount = getCheckingAccountREMOVELATER(userHandle);
                savingsAccount = getSavingsAccountREMOVELATER(userHandle);
                this.client.checkingAccountProperty().set(checkingAccount);
                this.client.savingsAccountProperty().set(savingsAccount);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void prepareTransactions(ObservableList<Transaction> transactions, int limit) {
        ResultSet resultSet = databaseDriver.getTransactions(this.client.userHandleProperty().get(), limit);
        if (resultSet == null) return; // What if there is no transaction history Patrick?
        try {
            while (resultSet.next()) {
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
                double amount = resultSet.getDouble("amount");
                LocalDate date = getDateFromResultSet(resultSet.getString("date"));
                String message = resultSet.getString("message");
                transactions.add(new Transaction(sender, receiver, amount, date, message));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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










    /*
    * Admin section
    */
    /**/

    public boolean authenticateAdmin(String username, String password) {
        return databaseDriver.adminExists(username, password);
    }

    public ObservableList<Client> getClientList() {
        return clientList;
    }

    public void setClientList() {
        List<Client> list = databaseDriver.getAllClients();
        clientList.addAll(list);
    }

    public ObservableList<Client> searchOne(String userHandle) {
        List<Client> list = databaseDriver.getOneClient(userHandle);
        return FXCollections.observableArrayList(list);
    }




    // Helper Methods

    public CheckingAccount getCheckingAccountREMOVELATER(String userHandle) {
//        CheckingAccount account = null;
//        ResultSet rs = databaseDriver.getCheckingAccountData(userHandle);
//        try {
//            String accountNum = rs.getString("account_number");
//            double balance = rs.getDouble("balance");
//            int transactionLimit = rs.getInt("transaction_limit");
//            account = new CheckingAccount(userHandle, accountNum, balance, transactionLimit);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return account;
        return databaseDriver.getCheckingAccount(userHandle);
    }

    public SavingsAccount getSavingsAccountREMOVELATER(String userHandle) {
//        SavingsAccount account = null;
//        ResultSet rs = databaseDriver.getSavingsAccountData(userHandle);
//        try {
//            String accountNum = rs.getString("account_number");
//            double balance = rs.getDouble("balance");
//            int withdrawalLimit = rs.getInt("withdrawal_limit");
//            account = new SavingsAccount(userHandle, accountNum, balance, withdrawalLimit);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return account;
        return databaseDriver.getSavingsAccount(userHandle);
    }




    private LocalDate getDateFromResultSet(String date) {
        String[] dateParts = date.split("-");
        return LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
    }

}
