package ru.easymoney.model.impl;

import ru.easymoney.model.Money;
import ru.easymoney.model.Payment;
import ru.easymoney.model.PaymentStorage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * In-memory implementation of PaymentStorage type.
 */
public final class SimplePaymentStorage implements PaymentStorage {

    private final List<Payment> payments;

    public SimplePaymentStorage() {
        this.payments = new CopyOnWriteArrayList<>();
    }

    @Override
    public final Money getWalletBalance(long walletId) {
        return payments.stream()
                .filter(p -> p.isParticipated(walletId))
                .map(p -> p.getAmount(walletId))
                .reduce((x, y) -> x.add(y))
                .orElse(new EmptyMoney());
    }

    @Override
    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }


}
