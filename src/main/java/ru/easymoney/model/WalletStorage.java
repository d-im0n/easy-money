package ru.easymoney.model;

/**
 * Representation of wallets storage
 */
public interface WalletStorage {

    /**
     * Check wallet existence
     * @param id checked wallet
     * @return {@code true} if there is such wallet in the storage,
     *          {@code false} if there is NO such wallet in the storage.
     */
    boolean isWalletExist(Long id);

    /**
     * Get wallet from the storage
     * @param id of wallet to be searched
     * @return
     */
    Wallet getWallet(Long id);

    /**
     * Create new wallet
     * @return created wallet id
     */
    long createWallet();
}
