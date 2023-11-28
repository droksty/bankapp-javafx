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
    public ResultSet getClientData(String username, String password) {
        Statement statement;
        ResultSet rs = null;
        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT  * FROM clients WHERE Username='"+username+"' AND Password='"+password+"';");
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
            rs = statement.executeQuery("SELECT  * FROM Admins WHERE Username='"+username+"' AND Password='"+password+"';");
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
                    "Clients (Firstname, Lastname, Username, Password, Date) " +
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
                    "CheckingAccounts (Owner, AccountNumber, TransactionLimit, Balance) " +
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
                    "SavingsAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) " +
                    "VALUES ('"+owner+"', '"+number+"', '"+withdrawalLimit+"', '"+balance+"')");
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
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
            id = resultSet.getInt("seq");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
