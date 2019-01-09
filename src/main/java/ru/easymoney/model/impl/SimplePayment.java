package ru.easymoney.model.impl;

import ru.easymoney.model.Money;
import ru.easymoney.model.Payment;

/**
 * Implementation of payment type. (immutable)
 */
public final class SimplePayment implements Payment {

    private final Money amount;
    private final long sourceWalletId;
    private final long targetWalletId;

    public SimplePayment(Money amount, long sourceWalletId, long targetWalletId) {
        if (amount == null)
            throw new IllegalArgumentException("Money amount value argument cannot be null.");

        this.amount = amount;
        this.sourceWalletId = sourceWalletId;
        this.targetWalletId = targetWalletId;
    }

    @Override
    public final Money getWriteOffAmount() {
        return this.amount.negative();
    }

    @Override
    public final Money getIncomingAmount() {
        return amount;
    }

    @Override
    public final Money getAmount(long walletId) {
        if (!isParticipated(walletId))
            throw new IllegalArgumentException("Incorrect wallet id.");

        if (isDifferentWallets()){
            return getFrom() == walletId ? getWriteOffAmount() : getIncomingAmount();
        }else{
            return new EmptyMoney();
        }
    }

    private boolean isDifferentWallets() {
        return getFrom() != getTo();
    }

    @Override
    public final long getFrom() {
        return sourceWalletId;
    }

    @Override
    public final long getTo() {
        return targetWalletId;
    }

    @Override
    public final boolean isParticipated(long walletId) {
        return getFrom() == walletId || getTo() == walletId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimplePayment that = (SimplePayment) o;

        if (sourceWalletId != that.sourceWalletId) return false;
        if (targetWalletId != that.targetWalletId) return false;
        return amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + (int) (sourceWalletId ^ (sourceWalletId >>> 32));
        result = 31 * result + (int) (targetWalletId ^ (targetWalletId >>> 32));
        return result;
    }
}
