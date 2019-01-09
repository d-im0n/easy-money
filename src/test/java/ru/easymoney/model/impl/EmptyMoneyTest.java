package ru.easymoney.model.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.easymoney.model.Money;

import static org.junit.Assert.*;

public class EmptyMoneyTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testEmptyAmount() {
        assertEquals("0", new EmptyMoney().getAmount());
    }

    @Test
    public void testAdd() {
        Money x = new SimpleMoney(5);

        Money empty = new EmptyMoney();

        assertEquals("5", empty.add(x).getAmount());
        assertEquals("5", x.add(empty).getAmount());
    }

    @Test
    public void testAddImmutability() {
        Money empty = new EmptyMoney();

        empty.add(new SimpleMoney(10));

        assertEquals("0", empty.getAmount());
    }

    @Test
    public void testNegative() {
        assertEquals("0", new EmptyMoney().negative().getAmount());
    }

    @Test
    public void testIsNegative() {
        assertFalse(new EmptyMoney().isNegative());
    }

    @Test
    public void testIsPositive() {
        assertFalse(new EmptyMoney().isPositive());
    }

    @Test
    public void testCompareToSameValue() {
        Money money = new EmptyMoney();
        assertEquals(0, money.compareTo(new SimpleMoney(0)));
    }

    @Test
    public void testCompareToBiggerValue() {
        Money money = new EmptyMoney();

        assertEquals(-1, money.compareTo(new SimpleMoney(10)));
    }

    @Test
    public void testCompareToSmallerValue() {
        Money money = new EmptyMoney();

        assertEquals(1, money.compareTo(new SimpleMoney(-10)));
    }

    @Test
    public void testCompareToNullValue() {
        Money money = new EmptyMoney();

        exceptionRule.expect(NullPointerException.class);

        money.compareTo(null);
    }

    @Test
    public void testEqualsSameValues() {
        Money money = new EmptyMoney();

        assertTrue(money.equals(new EmptyMoney()));
    }

    @Test
    public void testEqualsContract() {
        Money x = new EmptyMoney();
        Money y = new EmptyMoney();
        Money z = new EmptyMoney();

        assertTrue(x.equals(x));
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.equals(y) && y.equals(z) && x.equals(z));
        assertFalse(x.equals(null));

        assertTrue(x.hashCode() == y.hashCode());

    }

    @Test
    public void testEqualsContractWithDifferentTypes() {
        Money x = new EmptyMoney();
        Money y = new SimpleMoney(0);
        Money z = new SimpleMoney(0);

        assertTrue(x.equals(x));
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.equals(y) && y.equals(z) && x.equals(z));
        assertFalse(x.equals(null));

        assertTrue(x.hashCode() == y.hashCode());

    }

    @Test
    public void testEqualsSameValuesWithDifferentTypes() {
        Money money = new EmptyMoney();
        Money x = new SimpleMoney(0);

        assertTrue(money.equals(x));
        assertTrue(x.equals(money));
    }

}
