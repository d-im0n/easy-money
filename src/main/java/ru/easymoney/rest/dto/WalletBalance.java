package ru.easymoney.rest.dto;

public class WalletBalance {
    private long id;
    private String balance;

    public WalletBalance(){}

    public WalletBalance(long id, String balance) {
        this.id = id;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }
}
