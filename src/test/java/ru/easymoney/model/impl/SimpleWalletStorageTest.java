package ru.easymoney.model.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.easymoney.model.PaymentStorage;
import ru.easymoney.model.Wallet;
import ru.easymoney.model.WalletStorage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleWalletStorageTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testIsWalletExist() {
        WalletStorage store = new SimpleWalletStorage(new SimplePaymentStorage());
        long id = store.createWallet();

        assertEquals(1L, id);
        assertTrue(store.isWalletExist(1L));
        assertFalse(store.isWalletExist(2L));
    }

    @Test
    public void testGetExistingWallet() {
        WalletStorage store = new SimpleWalletStorage(new SimplePaymentStorage());
        long id = store.createWallet();

        assertEquals(1L, id);
        assertTrue(store.getWallet(1L) != null);
    }

    @Test
    public void testGetNotExistingWallet() {
        WalletStorage store = new SimpleWalletStorage(new SimplePaymentStorage());

        assertTrue(store.getWallet(1L) == null);
    }
}
