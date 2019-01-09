package ru.easymoney.model.impl;

import ru.easymoney.model.Money;
import ru.easymoney.model.PaymentStorage;
import ru.easymoney.model.Wallet;
import ru.easymoney.model.WalletStorage;
import ru.easymoney.model.exception.WalletException;

/**
 * Implementation of wallet type (immutable).
 */
public final class CustomerWallet implements Wallet {

    private final Long id;
    private final PaymentStorage paymentStorage;
    private final WalletStorage walletStorage;

    public CustomerWallet(long id, PaymentStorage paymentStorage, WalletStorage walletStorage) {
        this.id = id;
        this.paymentStorage = paymentStorage;
        this.walletStorage = walletStorage;
    }

    @Override
    public final Money getBalance() {
        synchronized (this) {
            return this.paymentStorage.getWalletBalance(this.id);
        }
    }

    @Override
    public final void sendMoney(long walletId, Money amount) {
        synchronized (this) {
            checkAmount(amount);
            checkTargetWallet(walletId);
            checkBalance(amount);

            paymentStorage.addPayment(new SimplePayment(amount, this.id, walletId));
        }
    }

    private void checkBalance(Money amount) {
        Money balance = getBalance();
        if (balance.compareTo(amount) == -1)
            throw new WalletException("Insufficient funds.");
    }

    private void checkTargetWallet(long walletId) {
        if (!walletStorage.isWalletExist(walletId))
            throw new WalletException(String.format("Wallet %d is not found.", walletId));
    }

    private void checkAmount(Money amount) {
        if (amount.isNegative())
            throw new WalletException("Money value cannot be negative.");
    }
}
