package ru.easymoney.model.impl;

import org.junit.Before;
import org.junit.Test;
import ru.easymoney.model.Wallet;
import ru.easymoney.model.WalletStorage;

import static org.junit.Assert.assertEquals;

public class TechnicalWalletTest {

    private TechnicalWallet technicalWallet;
    private WalletStorage walletStorage;

    @Before
    public void setUp() throws Exception {
        walletStorage = new SimpleWalletStorage(new SimplePaymentStorage());
        walletStorage.createWallet();

        technicalWallet = (TechnicalWallet)walletStorage.getWallet(0L);
    }

    @Test
    public void testBalance() {

        assertEquals(String.valueOf(Long.MAX_VALUE), technicalWallet.getBalance().getAmount());
    }

    @Test
    public void testUnchangableBalance() {
        Wallet customerWallet = walletStorage.getWallet(1L);

        assertEquals("0", customerWallet.getBalance().getAmount());

        technicalWallet.sendMoney(1, new SimpleMoney(Long.MAX_VALUE));

        assertEquals(String.valueOf(Long.MAX_VALUE), technicalWallet.getBalance().getAmount());
        assertEquals(String.valueOf(Long.MAX_VALUE), customerWallet.getBalance().getAmount());

        customerWallet.sendMoney(0, new SimpleMoney(100));

        assertEquals(String.valueOf(Long.MAX_VALUE), technicalWallet.getBalance().getAmount());
        assertEquals(String.valueOf(Long.MAX_VALUE - 100), customerWallet.getBalance().getAmount());
    }
}
