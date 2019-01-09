package ru.easymoney.model.impl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.easymoney.model.PaymentStorage;
import ru.easymoney.model.Wallet;
import ru.easymoney.model.WalletStorage;
import ru.easymoney.model.exception.WalletException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerWalletTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private WalletStorage wallets;

    @Before
    public void setUp() {
        PaymentStorage payments = new SimplePaymentStorage();
        payments.addPayment(new SimplePayment(new SimpleMoney(100), 3, 1));

        wallets = new SimpleWalletStorage(payments);
        wallets.createWallet();
        wallets.createWallet();
    }

    @Test
    public void testSendingMoneyToExistingWallet() {

        Wallet first = wallets.getWallet(1L);

        assertEquals("100", first.getBalance().getAmount());

        first.sendMoney(2, new SimpleMoney(40));

        assertEquals("60", first.getBalance().getAmount());
        assertEquals("40", wallets.getWallet(2L).getBalance().getAmount());
    }

    @Test
    public void testSendingMoneyWithNegativeAmount() {

        Wallet first = wallets.getWallet(1L);

        assertEquals("100", first.getBalance().getAmount());

        exceptionRule.expect(WalletException.class);
        exceptionRule.expectMessage("Money value cannot be negative.");

        first.sendMoney(2, new SimpleMoney(-30));

        assertEquals("100", first.getBalance().getAmount());
        assertEquals("0", wallets.getWallet(2L).getBalance().getAmount());
    }

    @Test
    public void testSendingMoneyToWrongWallet() {

        Wallet first = wallets.getWallet(1L);

        assertEquals("100", first.getBalance().getAmount());

        exceptionRule.expect(WalletException.class);
        exceptionRule.expectMessage("Wallet 3 is not found.");

        first.sendMoney(3, new SimpleMoney(30));

        assertEquals("100", first.getBalance().getAmount());
        assertEquals("0", wallets.getWallet(2L).getBalance().getAmount());
    }

    @Test
    public void testSendingMoneyWithWrongBalance() {

        Wallet first = wallets.getWallet(1L);

        assertEquals("100", first.getBalance().getAmount());

        exceptionRule.expect(WalletException.class);
        exceptionRule.expectMessage("Insufficient funds.");

        first.sendMoney(2, new SimpleMoney(200));

        assertEquals("100", first.getBalance().getAmount());
        assertEquals("0", wallets.getWallet(2L).getBalance().getAmount());
    }

    @Test
    public void testConcurrentSendingMoney() throws InterruptedException {
        int threadsCount = 2;
        ExecutorService service = Executors.newFixedThreadPool(threadsCount);
        List<Future<?>> futures = new ArrayList<>(threadsCount);

        for (int i = 0; i < threadsCount; ++i)
            futures.add(service.submit(() ->
                wallets.getWallet(1L).sendMoney(2, new SimpleMoney(70))
            ));


        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (ExecutionException e) {
                assertTrue(e.getCause() instanceof WalletException);
                assertEquals("Insufficient funds.", e.getCause().getMessage());
            }
        }

        assertEquals("30", wallets.getWallet(1L).getBalance().getAmount());
        assertEquals("70", wallets.getWallet(2L).getBalance().getAmount());
    }
}
