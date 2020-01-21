package com.piv.money.transfers.resource;

import com.piv.money.transfers.service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.piv.money.transfers.resource.Utils.prepareAmount;

/**
 * Created by Ivan on 21.01.2020.
 */
@Path("transfer")
public class TransferResource {
    @Inject
    private TransferService transferService;

    @POST
    @Path("/from/{fromId}/to/{toId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String create(@PathParam("fromId") String fromId, @PathParam("toId") String toId, String amount) throws ApplicationException {
        transferService.transfer(fromId, toId, prepareAmount(amount));
        return "ok";
    }
}
