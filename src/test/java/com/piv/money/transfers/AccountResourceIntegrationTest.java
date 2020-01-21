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
public class AccountResourceIntegrationTest {

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
    public void shouldReturnBadRequestIfAccountNotFound() throws Exception {
        HttpResponse response = findAccountResponse(ABSENT_ACCOUNT_ID);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldCreateNewAccountWithoutInitialValue() throws Exception {
        AccountDto created = createAccount(null);
        AccountDto persisted = findAccount(created.getId());
        assertEquals(created.getId(), persisted.getId());
        assertEquals(created.getAmount(), BigDecimal.ZERO);
        assertEquals(persisted.getAmount(), BigDecimal.ZERO);
    }

    @Test
    public void shouldCreateNewAccountWithInitialValue() throws Exception {
        BigDecimal initialValue = BigDecimal.TEN;
        AccountDto created = createAccount(initialValue.toPlainString());
        AccountDto persisted = findAccount(created.getId());
        assertEquals(created.getId(), persisted.getId());
        assertEquals(created.getAmount(), initialValue);
        assertEquals(persisted.getAmount(), initialValue);
    }

    @Test
    public void shouldReturnBadRequestIfAmountIsLessThanZero() throws Exception {
        HttpResponse response = createAccountResponse(NEGATIVE_NUMBER.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestIfAmountIsNotANumber() throws Exception {
        HttpResponse response = createAccountResponse(NOT_NUMBER);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldDepositToAccount() throws Exception {
        BigDecimal initialValue = BigDecimal.TEN;
        AccountDto created = createAccount(initialValue.toPlainString());
        BigDecimal delta = BigDecimal.ONE;
        depositAccount(created.getId(), delta.toPlainString());
        AccountDto changed = findAccount(created.getId());
        assertEquals(initialValue.add(delta), changed.getAmount());
    }

    @Test
    public void shouldReturnBadRequestForDepositIfAccountIsAbsent() throws Exception {
        BigDecimal delta = BigDecimal.ONE;
        HttpResponse response = depositAccountResponse(ABSENT_ACCOUNT_ID, delta.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForDepositIfAmountIsLessThanZero() throws Exception {
        AccountDto created = createAccount(null);
        HttpResponse response = depositAccountResponse(created.getId(), NEGATIVE_NUMBER.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForDepositIfAmountIsNotNumber() throws Exception {
        AccountDto created = createAccount(null);
        HttpResponse response = depositAccountResponse(created.getId(), NOT_NUMBER);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldWithdrawFromAccount() throws Exception {
        BigDecimal initialValue = BigDecimal.TEN;
        AccountDto created = createAccount(initialValue.toPlainString());
        BigDecimal delta = BigDecimal.ONE;
        withdrawAccount(created.getId(), delta.toPlainString());
        AccountDto changed = findAccount(created.getId());
        assertEquals(initialValue.subtract(delta), changed.getAmount());
    }

    @Test
    public void shouldReturnBadRequestForWithdrawIfAccountIsAbsent() throws Exception {
        BigDecimal delta = BigDecimal.ONE;
        HttpResponse response = withdrawAccountResponse(ABSENT_ACCOUNT_ID, delta.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForWithdrawIfAmountIsLessThanZero() throws Exception {
        AccountDto created = createAccount(null);
        HttpResponse response = withdrawAccountResponse(created.getId(), NEGATIVE_NUMBER.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForWithdrawIfAmountIsNotNumber() throws Exception {
        AccountDto created = createAccount(null);
        HttpResponse response = withdrawAccountResponse(created.getId(), NOT_NUMBER);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestForWithdrawIfAmountIsNotEnough() throws Exception {
        AccountDto created = createAccount(BigDecimal.ONE.toPlainString());
        BigDecimal delta = BigDecimal.TEN;
        HttpResponse response = withdrawAccountResponse(created.getId(), delta.toPlainString());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        AccountDto created = createAccount(null);
        deleteAccount(created.getId());
        HttpResponse response = findAccountResponse(created.getId());
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }
}
