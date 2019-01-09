package ru.easymoney.rest.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import ru.easymoney.rest.dto.WalletBalance;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WalletResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig().register(WalletResource.class);
    }

    @Test
    public void testCreateWallet() {
        Response response = target("/wallets").request().post(null);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertTrue(!response.getLocation().getPath().isEmpty());
    }

    @Test
    public void testGetBalanceForWrongWallet() {
        Response response = target("/wallets/99/balance").request().get();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetBalanceWithWrongParam() {
        Response response = target("/wallets/WRONG_NUMBER/balance").request().get();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetBalanceForExistingWallet() {
        Response walletsResponse = target("/wallets").request().post(null);

        WalletBalance balance = target(walletsResponse.getLocation().getPath() + "/balance").request().get(WalletBalance.class);

        assertEquals("0", balance.getBalance());
    }

    @Test
    public void testRechargingExistingWallet() {

        Response walletResponse = target("/wallets").request().post(null);

        Response rechargingResponse = target(walletResponse.getLocation().getPath() + "/recharging/100").request().get();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), rechargingResponse.getStatus());

        WalletBalance balance = target(walletResponse.getLocation().getPath() + "/balance").request().get(WalletBalance.class);

        assertEquals("100", balance.getBalance());
    }

    @Test
    public void testRechargingNotExistingWallet() {
        Response rechargingResponse = target("/wallets/199/recharging/100").request().get();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), rechargingResponse.getStatus());
        assertEquals("Wallet 199 is not found.", rechargingResponse.readEntity(String.class));
    }

    @Test
    public void testRechargingWithWrongAmount() {
        Response rechargingResponse = target("/wallets/1/recharging/WRONG_AMOUNT").request().get();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), rechargingResponse.getStatus());
    }

    @Test
    public void testRechargingWithNegativeAmount() {

    }
}
