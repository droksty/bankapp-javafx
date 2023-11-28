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

    /*
    * Admin Section
    * */
    public ResultSet getAdminData(String username, String password) {
        Statement statement;
        ResultSet rs = null;
        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT  * FROM admin WHERE username='"+username+"' AND password='"+password+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void createClient(String firstname, String lastname, String username, String password, LocalDate date) {
        Statement statement;
//        int KEY = -1;
        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "client (firstname, lastname, user_handle, password, date) " +
                    "VALUES ('"+firstname+"', '"+lastname+"', '"+username+"', '"+password+"', '"+date.toString()+"');", Statement.RETURN_GENERATED_KEYS);
            /*ResultSet set = statement.getGeneratedKeys();
            if (set.isBeforeFirst()) {
                KEY = set.getInt(1);
                System.out.println(KEY);
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return KEY;
    }

    public void createCheckingAccount(String owner, String number, double transactionLimit, double balance) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "checking_account (owner, account_number, transaction_limit, balance) " +
                    "VALUES ('"+owner+"', '"+number+"', '"+transactionLimit+"', '"+balance+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSavingsAccount(String owner, String number, double withdrawalLimit, double balance) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "savings_account (owner, account_number, withdrawal_limit, balance) " +
                    "VALUES ('"+owner+"', '"+number+"', '"+withdrawalLimit+"', '"+balance+"')");
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
