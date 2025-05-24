package org.webbee.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {
    private final LocalDateTime dateTime;
    private final String userName;
    private final TransactionType type;
    private final BigDecimal value;
    private final String anotherUserName;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(LocalDateTime dateTime, String userName, TransactionType type, BigDecimal value,
            String anotherUserName) {
        this.dateTime = dateTime;
        this.userName = userName;
        this.type = type;
        this.value = value;
        this.anotherUserName = anotherUserName;
    }

    public TransactionType getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public String getAnotherUserName() {
        return anotherUserName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public int compareTo(Transaction arg0) {
        return dateTime.compareTo(arg0.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, userName, type, value, anotherUserName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof Transaction))
            return false;
        var transaction = (Transaction) (obj);
        return dateTime.equals(transaction.dateTime) && userName.equals(transaction.userName)
                && type == transaction.type &&
                anotherUserName.equals(transaction.anotherUserName) && (value.compareTo(transaction.value) == 0);
    }

    @Override
    public String toString() {
        String dateTimeString = dateTime.format(dateTimeFormatter);
        return String.format("[%s] %s %s %s %s %s",
                dateTimeString,
                userName,
                type.toString(),
                value.toString(),
                type == TransactionType.TRANSFERRED ? "to" : "",
                type == TransactionType.TRANSFERRED ? anotherUserName : "");
    }
}
