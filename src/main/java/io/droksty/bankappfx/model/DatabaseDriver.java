package io.droksty.bankappfx.model;

import io.droksty.bankappfx.model.account.Account;
import io.droksty.bankappfx.model.account.CheckingAccount;
import io.droksty.bankappfx.model.account.SavingsAccount;
import io.droksty.bankappfx.model.client.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:bankappfx.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
    * Client Section
    * */
    public ResultSet getClientData(String userHandle, String password) {
        Statement statement;
        ResultSet rs = null;
        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT  * FROM client WHERE user_handle='"+userHandle+"' AND password='"+password+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getTransactions(String userHandle, int limit) {
        Statement statement;
        ResultSet rs = null;
        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM transaction WHERE sender='"+userHandle+"' OR receiver='"+userHandle+"' LIMIT "+limit+"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }




    /*                                                  */
    /*                  Admin DAO Section               */
    /*                                                  */

    public boolean adminExists(String username, String password) {
        String sql = "SELECT  * FROM admin WHERE username=? AND password=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertClient(String firstname, String lastname, String userHandle, String password, LocalDate date) {
        String sql = "INSERT INTO client (firstname, lastname, user_handle, password, date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, userHandle);
            ps.setString(4, password);
            ps.setString(5, date.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCheckingAccount(String owner, String number, int transactionLimit, double balance) {
        String sql = "INSERT INTO checking_account (owner, account_number, transaction_limit, balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, owner);
            ps.setString(2, number);
            ps.setInt(3, transactionLimit);
            ps.setDouble(4, balance);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSavingsAccount(String owner, String number, double withdrawalLimit, double balance) {
        String sql = "INSERT INTO savings_account (owner, account_number, withdrawal_limit, balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, owner);
            ps.setString(2, number);
            ps.setDouble(3, withdrawalLimit);
            ps.setDouble(4, balance);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String userHandle = rs.getString("user_handle");
                LocalDate date = getDateFromResultSet(rs.getString("date"));
                Account checkingAccount = getCheckingAccount(userHandle);
                Account savingsAccount = getSavingsAccount(userHandle);
                clientList.add(new Client(firstname, lastname, userHandle, checkingAccount, savingsAccount, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }







    




    public ResultSet getOneClient(String userHandle) {
        Statement statement;
        ResultSet rs = null;
        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM client WHERE user_handle='"+userHandle+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void depositSavings(String userHandle, double amount) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("UPDATE savings_account SET balance="+amount+" WHERE owner='"+userHandle+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }








    /*                                                  */
    /*                 Account DAO Section              */
    /*                                                  */

    public CheckingAccount getCheckingAccount(String userHandle) {
        CheckingAccount account = null;
        String sql = "SELECT * FROM checking_account WHERE owner=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userHandle);
            ResultSet rs = ps.executeQuery();
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
        String sql = "SELECT * FROM savings_account WHERE owner=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userHandle);
            ResultSet rs = ps.executeQuery();
            String accountNum = rs.getString("account_number");
            double balance = rs.getDouble("balance");
            double withdrawalLimit = rs.getInt("withdrawal_limit");
            account = new SavingsAccount(userHandle, accountNum, balance, withdrawalLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }


    /*
    * Utility Methods
    * */
    public int getLastClientID() {
        Statement statement;
        ResultSet resultSet;
        int id = 0;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='client';");
            id = resultSet.getInt("seq");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }











    private LocalDate getDateFromResultSet(String date) {
        String[] dateParts = date.split("-");
        return LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
    }
}
