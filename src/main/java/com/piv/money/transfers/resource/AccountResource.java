package com.piv.money.transfers.resource;

import com.piv.money.transfers.dto.AccountDto;
import com.piv.money.transfers.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.piv.money.transfers.resource.ApplicationException.ACCOUNT_IS_ABSENT;
import static com.piv.money.transfers.resource.Utils.prepareAmount;

/**
 * Created by Ivan on 19.01.2020.
 */
@Path("account")
public class AccountResource {
    @Inject
    private AccountService accountService;

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDto getAccount(@PathParam("accountId") String accountId) throws ApplicationException {
        AccountDto dto = accountService.get(accountId);
        if (dto == null) {
            throw new ApplicationException(String.format(ACCOUNT_IS_ABSENT, accountId));
        }
        return dto;
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDto create(String amount) throws ApplicationException {
        return accountService.create(prepareAmount(amount));
    }

    @POST
    @Path("/deposit/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDto deposit(@PathParam("accountId") String accountId, String amount) throws ApplicationException {
        return accountService.deposit(accountId, prepareAmount(amount));
    }

    @POST
    @Path("/withdraw/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDto withdraw(@PathParam("accountId") String accountId, String amount) throws ApplicationException {
        return accountService.withdraw(accountId, prepareAmount(amount));
    }

    @DELETE
    @Path("/delete/{accountId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(@PathParam("accountId") String accountId) {
        accountService.delete(accountId);
        return "ok";
    }
}
