package ru.easymoney.model.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.easymoney.model.Payment;

import static org.junit.Assert.*;

public class SimplePaymentTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testEqualsContract() {
        Payment x = new SimplePayment(new SimpleMoney(100), 1, 2);
        Payment y = new SimplePayment(new SimpleMoney(100), 1, 2);
        Payment z = new SimplePayment(new SimpleMoney(100), 1, 2);

        assertTrue(x.equals(x));
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.equals(y) && y.equals(z) && x.equals(z));
        assertFalse(x.equals(null));

        assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void testPositiveAmountValues() {
        Payment p = new SimplePayment(new SimpleMoney(100), 1, 2);

        assertEquals("-100", p.getWriteOffAmount().getAmount());
        assertEquals("100", p.getIncomingAmount().getAmount());
    }

    @Test
    public void testNegativeAmountValues() {
        Payment p = new SimplePayment(new SimpleMoney(-100), 1, 2);

        assertEquals("100", p.getWriteOffAmount().getAmount());
        assertEquals("-100", p.getIncomingAmount().getAmount());
    }

    @Test
    public void testNullAmountValue() {

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Money amount value argument cannot be null.");

        new SimplePayment(null, 1, 2);
    }

    @Test
    public void testGettingWalletsValues() {
        Payment p = new SimplePayment(new SimpleMoney(100), 1, 2);

        assertEquals(1, p.getFrom());
        assertEquals(2, p.getTo());
    }

    @Test
    public void testIsParticipated() {
        Payment p = new SimplePayment(new SimpleMoney(100), 1, 2);

        assertTrue(p.isParticipated(1));
        assertTrue(p.isParticipated(2));
        assertFalse(p.isParticipated(3));
    }

    @Test
    public void testIsNotParticipated() {
        Payment p = new SimplePayment(new SimpleMoney(100), 1, 2);

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Incorrect wallet id.");

        p.getAmount(3);
    }
}
