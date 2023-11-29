package io.droksty.bankappfx.model;

import java.sql.*;
import java.time.LocalDate;

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














    public ResultSet getAllClients() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT  * FROM client");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
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

    public ResultSet getCheckingAccountData(String usernameHandle) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM checking_account WHERE owner='"+usernameHandle+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getSavingsAccountData(String usernameHandle) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM savings_account WHERE owner='"+usernameHandle+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
