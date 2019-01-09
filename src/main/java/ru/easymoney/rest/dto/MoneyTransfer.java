package ru.easymoney.rest.dto;

import ru.easymoney.model.Money;
import ru.easymoney.model.impl.SimpleMoney;

public class MoneyTransfer {
    private long walletId;
    private String amount;

    public MoneyTransfer(){}

    public MoneyTransfer(long walletId, String amount) {
        this.walletId = walletId;
        this.amount = amount;
    }

    public long getWalletId() {
        return walletId;
    }

    public String getAmount() {
        return amount;
    }

    public Money getAmountAsMoney() {
        return new SimpleMoney(amount);
    }
}
