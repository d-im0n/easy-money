package ru.easymoney.model.impl;

import ru.easymoney.model.Money;

import java.math.BigInteger;

/**
 * Implementation of money type. (immutable)
 */
public final class SimpleMoney implements Money {

    private final BigInteger amount;

    private SimpleMoney(BigInteger amount) {
        this.amount = amount;
    }

    public SimpleMoney(long amount){
        this(BigInteger.valueOf(amount));
    }

    public SimpleMoney(int amount){
        this(BigInteger.valueOf(amount));
    }

    public SimpleMoney(String amount){
        if (amount == null || amount.isEmpty())
            throw new IllegalArgumentException("Money amount cannot be null or empty.");

        this.amount = new BigInteger(amount);
    }

    @Override
    public final String getAmount() {
        return this.amount.toString();
    }

    @Override
    public final Money add(Money value) {
        if (value == null)
            throw new IllegalArgumentException("Money value argument cannot be null.");

        return new SimpleMoney(
                this.amount.add(
                        new SimpleMoney(value.getAmount()).amount
                )
        );
    }

    @Override
    public final Money negative() {
        return new SimpleMoney(this.amount.negate());
    }

    @Override
    public boolean isNegative() {
        return this.amount.signum() == -1;
    }

    @Override
    public boolean isPositive() {
        return this.amount.signum() == 1;
    }

    @Override
    public int compareTo(Money o) {
        return this.amount.compareTo(new SimpleMoney(o.getAmount()).amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Money)) return false;

        SimpleMoney that = new SimpleMoney(((Money) o).getAmount());

        return amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return amount.toString().hashCode();
    }
}
