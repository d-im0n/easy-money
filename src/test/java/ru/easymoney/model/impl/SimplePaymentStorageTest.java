package ru.easymoney.model.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.easymoney.model.PaymentStorage;

import static org.junit.Assert.assertEquals;

public class SimplePaymentStorageTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testNotExistWalletBalance() {
        PaymentStorage ps = new SimplePaymentStorage();

        assertEquals("0", ps.getWalletBalance(1).getAmount());
    }

    @Test
    public void testExistWalletBalance() {
        PaymentStorage ps = new SimplePaymentStorage();
        ps.addPayment(new SimplePayment(new SimpleMoney(100), 1, 2));
        ps.addPayment(new SimplePayment(new SimpleMoney(200), 3, 1));

        assertEquals("100", ps.getWalletBalance(1).getAmount());
        assertEquals("100", ps.getWalletBalance(2).getAmount());
        assertEquals("-200", ps.getWalletBalance(3).getAmount());
    }

    @Test
    public void testAddNegativePayment() {
        PaymentStorage ps = new SimplePaymentStorage();
        ps.addPayment(new SimplePayment(new SimpleMoney(-100), 1, 2));

        assertEquals("100", ps.getWalletBalance(1).getAmount());
        assertEquals("-100", ps.getWalletBalance(2).getAmount());
    }

    @Test
    public void testAddNullPayment() {
        PaymentStorage ps = new SimplePaymentStorage();
        ps.addPayment(null);
        ps.addPayment(new SimplePayment(new SimpleMoney(100), 1, 2));

        exceptionRule.expect(NullPointerException.class);

        ps.getWalletBalance(1);
    }

    @Test
    public void testAddSelfPayment() {
        PaymentStorage ps = new SimplePaymentStorage();
        ps.addPayment(new SimplePayment(new SimpleMoney(100), 1, 1));

        assertEquals("0", ps.getWalletBalance(1).getAmount());
    }
}
