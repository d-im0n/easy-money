package ru.easymoney.model.impl;

import ru.easymoney.model.Money;
import ru.easymoney.model.PaymentStorage;
import ru.easymoney.model.Wallet;
import ru.easymoney.model.WalletStorage;
import ru.easymoney.model.exception.WalletException;

/**
 * Implementation of wallet type for technical purposes.
 * For example, to recharge another wallet.
 * (immutable)
 */
public final class TechnicalWallet implements Wallet {

    private final Long id;
    private final PaymentStorage paymentStorage;
    private final WalletStorage walletStorage;

    public TechnicalWallet(long id, PaymentStorage paymentStorage, WalletStorage walletStorage) {
        this.id = id;
        this.paymentStorage = paymentStorage;
        this.walletStorage = walletStorage;
    }

    @Override
    public final Money getBalance() {
        return new SimpleMoney(Long.MAX_VALUE);
    }

    @Override
    public final void sendMoney(long walletId, Money amount) {
        synchronized (this) {
            checkAmount(amount);
            checkTargetWallet(walletId);

            paymentStorage.addPayment(new SimplePayment(amount, this.id, walletId));
        }
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
