package ru.easymoney.model;

import ru.easymoney.model.exception.WalletException;

/**
 * Representation of money wallet type.
 */
public interface Wallet {

    /**
     * Returns actual wallet balance
     * @return actual wallet balance
     */
    Money getBalance();

    /**
     * Sending money from current wallet to another one.
     * There is no possibility to send negative amount
     * @param walletId target wallet's id
     * @param amount amount of money to be sent
     * @throws WalletException if target wallet is not found,
     *         amount of money is negative or source wallet does't have enough money
     */
    void sendMoney(long walletId, Money amount);
}
