package org.weebee.models;

import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

public class User {
    private SortedSet<Transaction> transactions = new TreeSet<Transaction>();
    private BigDecimal balance;
    private final String name;
    // private Transaction lastInquiryTransaction;

    public User(String name) {
        this.name = name;
        balance = new BigDecimal(0);
        // lastInquiryTransaction = null;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public SortedSet<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        // if (transaction == null)
        // throw new Exception("Adding null transaction");
        transactions.add(transaction);
    }

    public void balanceSummary() {
        balance = new BigDecimal(0);
        transactions.stream().forEach(transaction -> {
            switch (transaction.getType()) {
                case INQUIRY:
                    balance = new BigDecimal(transaction.getValue().toString());
                    break;
                case TRANSFERRED:
                    if (!transaction.getUserName().equals(transaction.getAnotherUserName()))
                        if (name.equals(transaction.getUserName()))
                            balance = balance.subtract(transaction.getValue());
                        else if (name.equals(transaction.getAnotherUserName()))
                            balance = balance.add(transaction.getValue());
                    break;
                case WINHDREW:
                    balance = balance.subtract(transaction.getValue());
                    break;
                default:
                    break;
            }
        });
    }
}