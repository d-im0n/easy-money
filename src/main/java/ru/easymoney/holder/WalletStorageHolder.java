package ru.easymoney.holder;

import ru.easymoney.model.PaymentStorage;
import ru.easymoney.model.WalletStorage;
import ru.easymoney.model.impl.SimplePaymentStorage;
import ru.easymoney.model.impl.SimpleWalletStorage;

public class WalletStorageHolder {

    private static final PaymentStorage paymentStorage = new SimplePaymentStorage();
    private static volatile WalletStorage walletStorage;

    public static WalletStorage getInstance(){
        WalletStorage instance = walletStorage;
        if (instance == null) {
            synchronized (WalletStorage.class) {
                instance = walletStorage;
                if (instance == null) {
                    walletStorage = instance = new SimpleWalletStorage(paymentStorage);
                }
            }
        }
        return instance;
    }
}
