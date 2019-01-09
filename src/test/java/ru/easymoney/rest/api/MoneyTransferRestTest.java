package ru.easymoney.rest.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import ru.easymoney.rest.dto.WalletBalance;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class MoneyTransferRestTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig().register(WalletResource.class);
    }

    @Test
    public void testMoneyTranferWithExistingWalletsAndValidBalance() {
        Response sourceWalletResponse = createWallet();

        rechargeSourceWallet(sourceWalletResponse);

        Response targetWalletResponse = createWallet();

        Response transferResponse = transferMoney(sourceWalletResponse, targetWalletResponse, "243");

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), transferResponse.getStatus());

        checkBalance(targetWalletResponse, "243");

        checkBalance(sourceWalletResponse, "257");
    }

    @Test
    public void testTransferWithExistingWalletsAndWrongBalance() {
        Response sourceWalletResponse = createWallet();

        Response targetWalletResponse = createWallet();

        Response transferResponse = transferMoney(sourceWalletResponse, targetWalletResponse, "243");

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), transferResponse.getStatus());
        assertEquals("Insufficient funds.", transferResponse.readEntity(String.class));
    }

    @Test
    public void testTransferWithWrongSourceWallet() {
        Response targetWalletResponse = createWallet();

        Entity<String> entity = Entity.json(String.format("{\"walletId\":%s,\"amount\":\"243\"}", getWalletId(targetWalletResponse)));

        Response transferResponse = target("/wallets/45/payment").request().post(entity);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), transferResponse.getStatus());
    }

    @Test
    public void testTransferWithWrongTargetWallet() {
        Response sourceWalletResponse = createWallet();

        rechargeSourceWallet(sourceWalletResponse);

        Entity<String> entity = Entity.json(String.format("{\"walletId\":78,\"amount\":\"243\"}"));

        Response transferResponse = target(sourceWalletResponse.getLocation().getPath() + "/payment").request().post(entity);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), transferResponse.getStatus());
        assertEquals("Wallet 78 is not found.", transferResponse.readEntity(String.class));
    }

    @Test
    public void testTransferWithExistingWalletsAndWrongAmount() {
        Response sourceWalletResponse = createWallet();

        rechargeSourceWallet(sourceWalletResponse);

        Response targetWalletResponse = createWallet();

        Response transferResponse = transferMoney(sourceWalletResponse, targetWalletResponse, "-100");

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), transferResponse.getStatus());
        assertEquals("Money value cannot be negative.", transferResponse.readEntity(String.class));
    }

    private void checkBalance(Response targetWalletResponse, String s) {
        WalletBalance targetBalance = target(targetWalletResponse.getLocation().getPath() + "/balance").request().get(WalletBalance.class);

        assertEquals(s, targetBalance.getBalance());
    }

    private Response transferMoney(Response sourceWalletResponse, Response targetWalletResponse, String amount) {

        String targetWalletId = getWalletId(targetWalletResponse);

        Entity<String> entity = Entity.json(String.format("{\"walletId\":%s,\"amount\":\"%s\"}", targetWalletId, amount));

        return target(sourceWalletResponse.getLocation().getPath() + "/payment").request().post(entity);
    }

    private Response createWallet() {
        Response walletResponse = target("/wallets").request().post(null);

        checkBalance(walletResponse, "0");

        return walletResponse;
    }

    private void rechargeSourceWallet(Response walletResponse) {
        Response rechargingResponse = target(walletResponse.getLocation().getPath() + "/recharging/500").request().get();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), rechargingResponse.getStatus());

        checkBalance(walletResponse, "500");
    }

    private String getWalletId(Response walletResponse){
        String[] segments = walletResponse.getLocation().getPath().split("/");

        return segments[segments.length-1];
    }
}
