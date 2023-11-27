package io.droksty.bankappfx.model.transaction;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transaction {
    private final StringProperty sender;
    private final StringProperty receiver;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;

    public Transaction(String sender, String receiver, double amount, LocalDate date, String message) {
        this.sender = new SimpleStringProperty(this, "sender", sender);
        this.receiver = new SimpleStringProperty(this, "receiver", receiver);
        this.amount = new SimpleDoubleProperty(this, "amount", amount);
        this.date = new SimpleObjectProperty<>(this, "date", date);
        this.message = new SimpleStringProperty(this, "message", message);
    }

    public StringProperty senderProperty() {
        return sender;
    }
    public StringProperty receiverProperty() {
        return receiver;
    }
    public DoubleProperty amountProperty() {
        return amount;
    }
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    public StringProperty messageProperty() {
        return message;
    }
}
