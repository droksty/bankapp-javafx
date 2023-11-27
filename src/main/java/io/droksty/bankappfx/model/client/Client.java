package io.droksty.bankappfx.model.client;

import io.droksty.bankappfx.model.account.Account;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstname;
    private final StringProperty lastname;
    private final StringProperty username;
    private final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingsAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String firstname, String lastname, String username, Account checkingAccount, Account savingsAccount, LocalDate dateCreated) {
        this.firstname = new SimpleStringProperty(this, "Firstname", firstname);
        this.lastname = new SimpleStringProperty(this, "Lastname", lastname);
        this.username = new SimpleStringProperty(this, "Username", username);
        this.checkingAccount = new SimpleObjectProperty<>(this, "Checking Account", checkingAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this, "Savings Account", savingsAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date Created", dateCreated);
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }
    public StringProperty lastnameProperty() {
        return lastname;
    }
    public StringProperty usernameProperty() {
        return username;
    }
    public ObjectProperty<Account> checkingAccountProperty() {
        return checkingAccount;
    }
    public ObjectProperty<Account> savingsAccountProperty() {
        return savingsAccount;
    }
    public ObjectProperty<LocalDate> dateCreatedProperty() {
        return dateCreated;
    }
}
