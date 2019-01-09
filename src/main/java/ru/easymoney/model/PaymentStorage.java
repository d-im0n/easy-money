package ru.easymoney.model;

/**
 * Representation of user transactions storage
 */
public interface PaymentStorage {

    /**
     * Returns actual balance of certain wallet
     * @param walletId id of considered wallet
     * @return actual balance of certain wallet
     */
    Money getWalletBalance(long walletId);

    /**
     * Add new payment to the storage
     * @param payment to be added
     */
    void addPayment(Payment payment);
}
