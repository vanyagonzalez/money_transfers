package com.piv.money.transfers;

import com.piv.money.transfers.dto.AccountDto;
import org.apache.http.HttpResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static com.piv.money.transfers.UtilsTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by Ivan on 19.01.2020.
 */
public class TransferResourceIntegrationTest {

    private static MoneyTransfersServer jettyServer;

    @BeforeClass
    public static void setup() throws Exception {
        jettyServer = new MoneyTransfersServer();
        jettyServer.start();
    }

    @AfterClass
    public static void cleanup() throws Exception {
        jettyServer.stop();
    }

    @Test
    public void shouldTransferAmountBetweenAccounts() throws Exception {
        AccountDto from = createAccount(BigDecimal.TEN.toPlainString());
        AccountDto to = createAccount(BigDecimal.TEN.toPlainString());
        BigDecimal delta = BigDecimal.ONE;
        transferAmount(from.getId(), to.getId(), delta.toPlainString());
        AccountDto changedFrom = findAccount(from.getId());
        AccountDto changedTo = findAccount(to.getId());
        assertEquals(from.getAmount().subtract(delta), changedFrom.getAmount());
        assertEquals(to.getAmount().add(delta), changedTo.getAmount());
    }

    @Test
    public void shouldReturnBadRequestForTransferIfFromAccountIsAbsent() throws Exception {
        AccountDto to = createAccount(BigDecimal.TEN.toPlainString());
        BigDecimal delta = BigDecimal.ONE;
        HttpResponse response = transferAmountResponse(ABSENT_ACCOUNT_ID, to.getId(), delta.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForTransferIfToAccountIsAbsent() throws Exception {
        AccountDto from = createAccount(BigDecimal.TEN.toPlainString());
        BigDecimal delta = BigDecimal.ONE;
        HttpResponse response = transferAmountResponse(from.getId(), ABSENT_ACCOUNT_ID, delta.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForTransferIfAmountIsNegative() throws Exception {
        AccountDto from = createAccount(BigDecimal.TEN.toPlainString());
        AccountDto to = createAccount(BigDecimal.TEN.toPlainString());
        HttpResponse response = transferAmountResponse(from.getId(), to.getId(), NEGATIVE_NUMBER.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForTransferIfAmountIsNotNumber() throws Exception {
        AccountDto from = createAccount(BigDecimal.TEN.toPlainString());
        AccountDto to = createAccount(BigDecimal.TEN.toPlainString());
        HttpResponse response = transferAmountResponse(from.getId(), to.getId(), NOT_NUMBER);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForTransferIfFromAccountDoesNotHaveEnoughAmount() throws Exception {
        AccountDto from = createAccount(BigDecimal.ONE.toPlainString());
        AccountDto to = createAccount(BigDecimal.ONE.toPlainString());
        BigDecimal delta = BigDecimal.TEN;
        HttpResponse response = transferAmountResponse(from.getId(), to.getId(), delta.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }
}
