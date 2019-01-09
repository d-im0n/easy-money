package ru.easymoney.rest.api;

import ru.easymoney.holder.WalletStorageHolder;
import ru.easymoney.model.Wallet;
import ru.easymoney.model.WalletStorage;
import ru.easymoney.model.exception.WalletException;
import ru.easymoney.model.impl.SimpleMoney;
import ru.easymoney.rest.dto.MoneyTransfer;
import ru.easymoney.rest.dto.WalletBalance;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("wallets")
@Produces(MediaType.APPLICATION_JSON)
public class WalletResource {

    private final WalletStorage walletStorage = WalletStorageHolder.getInstance();

    @POST
    public Response create(@Context UriInfo uriInfo){
        long id = walletStorage.createWallet();

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(id));
        return Response.created(builder.build()).build();
    }

    @GET
    @Path("/{id}/balance")
    public Response getBalance(@PathParam("id") long id){
        Wallet wallet = walletStorage.getWallet(id);
        if (wallet == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        String amount = wallet.getBalance().getAmount();

        return Response.ok().entity(new WalletBalance(id, amount)).build();
    }


    @GET
    @Path("/{id}/recharging/{amount}")
    public Response recharge(@PathParam("id") long id, @PathParam("amount") long amount){
        Wallet technicalWallet = walletStorage.getWallet(0L);

        try {
            technicalWallet.sendMoney(id, new SimpleMoney(String.valueOf(amount)));

            return Response.noContent().build();
        }catch(WalletException e){

            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{id}/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMoney(@PathParam("id") long id, MoneyTransfer moneyTransfer){
        Wallet wallet = walletStorage.getWallet(id);
        if (wallet == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        try {
            wallet.sendMoney(moneyTransfer.getWalletId(), moneyTransfer.getAmountAsMoney());

            return Response.noContent().build();
        }catch(WalletException e){
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
