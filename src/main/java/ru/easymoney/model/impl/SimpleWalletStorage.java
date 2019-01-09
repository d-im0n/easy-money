package ru.easymoney.model.impl;

import ru.easymoney.model.PaymentStorage;
import ru.easymoney.model.Wallet;
import ru.easymoney.model.WalletStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of WalletStorage type.
 */
public final class SimpleWalletStorage implements WalletStorage {

    private final PaymentStorage paymentStorage;

    private final Map<Long, Wallet> wallets;

    public SimpleWalletStorage(PaymentStorage paymentStorage) {
        this.wallets = new ConcurrentHashMap<>();
        this.paymentStorage = paymentStorage;

        putTechnicalWallet(paymentStorage);
    }

    private void putTechnicalWallet(PaymentStorage paymentStorage) {
        long id = 0;
        this.wallets.put(id, new TechnicalWallet(id, paymentStorage, this));
    }

    @Override
    public boolean isWalletExist(Long id) {
        return this.wallets.containsKey(id);
    }

    @Override
    public Wallet getWallet(Long id) {
        return this.wallets.get(id);
    }

    @Override
    public long createWallet(){
        synchronized (this) {
            long id = wallets.size();
            this.wallets.put(id, new CustomerWallet(id, paymentStorage, this));
            return id;
        }
    }
}
