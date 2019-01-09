package ru.easymoney.model;

/**
 * Representation of money type.
 */
public interface Money extends Comparable<Money> {

    /**
     * Money amount.
     * @return amount as String value
     */
    String getAmount();

    /**
     * Returns a Money whose value is {@code (this + val)}.
     * @param value value to be added to this BigInteger.
     * @return {@code this + value}
     */
    Money add(Money value);

    /**
     * Returns a Money whose value is {@code (-this)}.
     * @return {@code -this}
     */
    Money negative();

    /**
     * Returns {@code true} if this Money value is below zero,
     *          {@code false} if it equals or above zero.
     *
     * @return {@code true} if this Money value is below zero,
     *          {@code false} if it equals or above zero.
     */
    boolean isNegative();

    /**
     * Returns {@code true} if this Money value is above zero,
     *          {@code false} if it equals or below zero.
     *
     * @return {@code true} if this Money value is above zero,
     *          {@code false} if it equals or below zero.
     */
    boolean isPositive();
}
