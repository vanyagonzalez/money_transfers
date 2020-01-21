package com.piv.money.transfers.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Ivan on 19.01.2020.
 */
@Provider
public class ApplicationException extends Exception implements ExceptionMapper<ApplicationException> {
    private static final long serialVersionUID = 1L;

    public static String ACCOUNT_IS_ABSENT = "Account [%s] is absent.";
    public static String ACCOUNT_HAS_NOT_ENOUGH_AMOUNT = "Account [%s] has not enough amount [%s]. Required amount [%s].";
    public static String AMOUNT_IS_LESS_THAN_ZERO = "Amount [%s] is less than 0";
    public static String AMOUNT_IS_NOT_A_NUMBER = "Amount [%s] is not a number";

    public ApplicationException() {
        super("Something wrong in Application");
    }

    public ApplicationException(String message) {
        super(message);
    }

    public Response toResponse(ApplicationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).type("text/plain").build();
    }
}
