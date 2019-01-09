package ru.easymoney.model;

/**
 * Simple representation of transaction between two wallets.
 */
public interface Payment {

    /**
     * Returns amount which will be written off from source wallet
     * @return opposite Money value (Money#negative() will be called)
     */
    Money getWriteOffAmount();

    /**
     * Returns amount which will be received be target wallet
     * @return
     */
    Money getIncomingAmount();

    /**
     *
     * @param walletId to be checked in target and source wallet ids
     * @return write off amount if walletId is source wallet,
     *         incoming amount if walletId is target wallet,
     *         EmptyMoney value will be return if source's id and target's id are the same
     *
     * @throws IllegalArgumentException if wallet doesn't match source or target wallet ids
     */
    Money getAmount(long walletId);

    /**
     * Target wallet id
     * @return target wallet id
     */
    long getFrom();

    /**
     * Returns source wallet id
     * @return source wallet id
     */
    long getTo();

    /**
     * Check wallet participation
     * @param walletId to be checked in target and source wallet ids
     * @return {@code true} if walletId matches with source or target ids
     *          {@code false} otherwise
     */
    boolean isParticipated(long walletId);


}
