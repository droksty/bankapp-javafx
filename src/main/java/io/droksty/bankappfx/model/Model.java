package io.droksty.bankappfx.model;

import io.droksty.bankappfx.model.account.CheckingAccount;
import io.droksty.bankappfx.model.account.SavingsAccount;
import io.droksty.bankappfx.model.client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final DatabaseDriver databaseDriver;
    private final Client client;
    private final ObservableList<Client> clientList;

    private Model() {
        this.databaseDriver = new DatabaseDriver();
        this.client = new Client("", "", "", null, null, null);
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

    public boolean isClientLoginAuthorised(String userHandle, String password) {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet rs = databaseDriver.getClientData(userHandle, password);
        try {
            if (rs.isBeforeFirst()) {
                this.client.firstnameProperty().set(rs.getString("Firstname"));
                this.client.lastnameProperty().set(rs.getString("Lastname"));
                this.client.userHandleProperty().set(rs.getString("Username"));
                this.client.dateCreatedProperty().set(getDateFromResultSet(rs.getString("Date")));
                checkingAccount = getCheckingAccount(userHandle);
                savingsAccount = getSavingsAccount(userHandle);
                this.client.checkingAccountProperty().set(checkingAccount);
                this.client.checkingAccountProperty().set(savingsAccount);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /*
    * Admin section
    */

    public boolean isAdminLoginAuthorised(String username, String password) {
        ResultSet rs = databaseDriver.getAdminData(username, password);
        try {
            if (rs.isBeforeFirst()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ObservableList<Client> getClientList() {
        return clientList;
    }

    public void setClientList() {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet rs = databaseDriver.getAllClients();
        try {
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String userHandle = rs.getString("user_handle");
                LocalDate date = getDateFromResultSet(rs.getString("date"));
                checkingAccount = getCheckingAccount(userHandle);
                savingsAccount = getSavingsAccount(userHandle);
                clientList.add(new Client(firstname, lastname, userHandle, checkingAccount, savingsAccount, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    // Helper Methods

    public CheckingAccount getCheckingAccount(String userHandle) {
        CheckingAccount account = null;
        ResultSet rs = databaseDriver.getCheckingAccountData(userHandle);
        try {
            String accountNum = rs.getString("account_number");
            double balance = rs.getDouble("balance");
            int transactionLimit = rs.getInt("transaction_limit");
            account = new CheckingAccount(userHandle, accountNum, balance, transactionLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public SavingsAccount getSavingsAccount(String userHandle) {
        SavingsAccount account = null;
        ResultSet rs = databaseDriver.getSavingsAccountData(userHandle);
        try {
            String accountNum = rs.getString("account_number");
            double balance = rs.getDouble("balance");
            int withdrawalLimit = rs.getInt("withdrawal_limit");
            account = new SavingsAccount(userHandle, accountNum, balance, withdrawalLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }




    private LocalDate getDateFromResultSet(String date) {
        String[] dateParts = date.split("-");
        return LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
    }

}
