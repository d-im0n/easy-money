package ru.easymoney.model.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.easymoney.model.Money;

import java.math.BigInteger;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class SimpleMoneyTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testInitWithInt() {
        Money money = new SimpleMoney(100);

        assertEquals("100", money.getAmount());
    }

    @Test
    public void testInitWithLong() {
        Money money = new SimpleMoney(100L);

        assertEquals("100", money.getAmount());
    }

    @Test
    public void testInitWithCorrectString() {
        Money money = new SimpleMoney("100");

        assertEquals("100", money.getAmount());
    }

    @Test
    public void testInitWithIncorrectString() {

        exceptionRule.expect(NumberFormatException.class);

        new SimpleMoney( "some text");
    }

    @Test
    public void testInitWithEmptyString() {

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Money amount cannot be null or empty.");

        new SimpleMoney( "");
    }

    @Test
    public void testInitWithNull() {

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Money amount cannot be null or empty.");

        new SimpleMoney( null);
    }

    @Test
    public void testAddImmutability() {
        Money x = new SimpleMoney(100);

        Money y = x.add(new SimpleMoney(50));

        assertFalse(x == y);
        assertEquals("100", x.getAmount());
    }

    @Test
    public void testAddPositiveValue() {
        Money money = new SimpleMoney(100);

        assertEquals("120", money.add(new SimpleMoney(20)).getAmount());
    }

    @Test
    public void testAddNegativeValue() {
        Money money = new SimpleMoney(100);

        assertEquals("80", money.add(new SimpleMoney(-20)).getAmount());
    }

    @Test
    public void testAddMaxLongValues() {
        Money money = new SimpleMoney(Long.MAX_VALUE);

        assertEquals(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.valueOf(Long.MAX_VALUE)).toString(),
                money.add(new SimpleMoney(Long.MAX_VALUE)).getAmount());
    }

    @Test
    public void testAddMinLongValues() {
        Money money = new SimpleMoney(Long.MIN_VALUE);

        assertEquals(BigInteger.valueOf(Long.MIN_VALUE).add(BigInteger.valueOf(Long.MIN_VALUE)).toString(),
                money.add(new SimpleMoney(Long.MIN_VALUE)).getAmount());
    }


    @Test
    public void testAddNullValue() {
        Money money = new SimpleMoney(0);

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Money value argument cannot be null.");

        money.add(null);
    }

    @Test
    public void testNegative() {
        Money money = new SimpleMoney(40);

        assertEquals("-40", money.negative().getAmount());
    }

    @Test
    public void testNegativeZeroValue() {
        Money money = new SimpleMoney(0);

        assertEquals("0", money.negative().getAmount());
    }

    @Test
    public void testNagetiveImmutability() {
        Money x = new SimpleMoney(40);

        Money y = x.negative();

        assertFalse(x == y);
        assertEquals("40", x.getAmount());
    }

    @Test
    public void testCompareToSameValue() {
        Money money = new SimpleMoney(5);
        assertEquals(0, money.compareTo(new SimpleMoney(5)));
    }

    @Test
    public void testCompareToBiggerValue() {
        Money money = new SimpleMoney(5);
        
        assertEquals(-1, money.compareTo(new SimpleMoney(10)));
    }

    @Test
    public void testCompareToSmallerValue() {
        Money money = new SimpleMoney(5);

        assertEquals(1, money.compareTo(new SimpleMoney(3)));
    }

    @Test
    public void testCompareToNullValue() {
        Money money = new SimpleMoney(5);

        exceptionRule.expect(NullPointerException.class);

        money.compareTo(null);

    }

    @Test
    public void testEqualsSameValues() {
        Money money = new SimpleMoney(5);

        assertEquals(true, money.equals(new SimpleMoney(5)));
    }

    @Test
    public void testEqualsDifferentValues() {
        Money money = new SimpleMoney(5);

        assertEquals(false, money.equals(new SimpleMoney(10)));
    }

    @Test
    public void testNegativeValue() {
        Money money = new SimpleMoney(-5);

        assertTrue(money.isNegative());
        assertFalse(money.isPositive());
    }

    @Test
    public void testIsPositiveValue() {
        Money money = new SimpleMoney(5);

        assertFalse(money.isNegative());
        assertTrue(money.isPositive());
    }

    @Test
    public void testEqualsContract() {
        Money x = new SimpleMoney(50);
        Money y = new SimpleMoney(50);
        Money z = new SimpleMoney(50);

        assertTrue(x.equals(x));
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.equals(y) && y.equals(z) && x.equals(z));
        assertFalse(x.equals(null));

        assertTrue(x.hashCode() == y.hashCode());

    }
}
