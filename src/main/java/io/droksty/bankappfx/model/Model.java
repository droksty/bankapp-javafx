package io.droksty.bankappfx.model;

import io.droksty.bankappfx.model.client.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final DatabaseDriver databaseDriver;
    private final Client client;

    private Model() {
        this.databaseDriver = new DatabaseDriver();
        this.client = new Client("", "", "", null, null, null);
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

    public boolean isClientLoginAuthorised(String username, String password) {
        ResultSet rs = databaseDriver.getClientData(username, password);
        try {
            if (rs.isBeforeFirst()) {
                this.client.firstnameProperty().set(rs.getString("Firstname"));
                this.client.lastnameProperty().set(rs.getString("Lastname"));
                this.client.usernameProperty().set(rs.getString("Username"));
                this.client.dateCreatedProperty().set(getDateFromResultSet(rs.getString("Date")));
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


    // Helper Methods
    private LocalDate getDateFromResultSet(String date) {
        String[] dateParts = date.split("-");
        return LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
    }
}
