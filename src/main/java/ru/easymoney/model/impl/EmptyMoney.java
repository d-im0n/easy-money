package ru.easymoney.model.impl;

import ru.easymoney.model.Money;

/**
 * Implementation of money type with constant zero amount. (immutable)
 */
public final class EmptyMoney implements Money {

    private final String amount = "0";

    @Override
    public final String getAmount() {
        return amount;
    }

    @Override
    public final Money add(Money value) {
        return value;
    }

    @Override
    public final Money negative() {
        return new EmptyMoney();
    }

    @Override
    public final boolean isNegative() {
        return false;
    }

    @Override
    public final boolean isPositive() {
        return false;
    }

    @Override
    public final int compareTo(Money o) {

        if (amount.equals(o.getAmount()))
            return 0;
        else if(o.isNegative())
            return 1;
        else
            return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Money)) return false;

        Money that = (Money) o;

        return amount.equals(that.getAmount());
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }
}
