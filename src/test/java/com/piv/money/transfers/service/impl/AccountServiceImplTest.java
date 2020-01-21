package com.piv.money.transfers.service.impl;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.dto.AccountDto;
import com.piv.money.transfers.resource.ApplicationException;
import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.model.impl.inmemory.AccountInMemory;
import com.piv.money.transfers.service.AccountService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static com.piv.money.transfers.UtilsTest.ABSENT_ACCOUNT_ID;
import static com.piv.money.transfers.UtilsTest.TEST_ACCOUNT_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by Ivan on 18.01.2020.
 */
public class AccountServiceImplTest {

    private static Account testAccount;
    private static AccountService accountService;

    @BeforeClass
    public static void setup() throws Exception {
        AccountDao accountDao = Mockito.mock(AccountDao.class);
        testAccount = new AccountInMemory(TEST_ACCOUNT_ID, BigDecimal.TEN);
        when(accountDao.create(any(BigDecimal.class))).thenReturn(testAccount);
        when(accountDao.read(TEST_ACCOUNT_ID)).thenReturn(testAccount);
        when(accountDao.read(ABSENT_ACCOUNT_ID)).thenReturn(null);
        when(accountDao.updateAccount(any(Account.class))).thenReturn(testAccount);
        doNothing().when(accountDao).delete(any(String.class));

        accountService = new AccountServiceImpl(accountDao);
    }

    @Test
    public void shouldCreateAccountWithInitialAmount() throws Exception {
        AccountDto dto = accountService.create(BigDecimal.ONE);
        assertNotNull(dto);
        assertEquals(testAccount.getId(), dto.getId());
        assertEquals(testAccount.getAmount(), dto.getAmount());
    }

    @Test
    public void shouldReturnAccountIfItPresets() {
        AccountDto dto = accountService.get(TEST_ACCOUNT_ID);
        assertNotNull(dto);
        assertEquals(testAccount.getId(), dto.getId());
    }

    @Test
    public void shouldReturnNullIfItIsAbsent() {
        AccountDto dto = accountService.get(ABSENT_ACCOUNT_ID);
        assertNull(dto);
    }

    @Test
    public void shouldDepositToAccount() throws Exception {
        BigDecimal beforeDeposit = testAccount.getAmount();
        BigDecimal delta = BigDecimal.ONE;
        AccountDto dto = accountService.deposit(TEST_ACCOUNT_ID, delta);
        assertEquals(beforeDeposit.add(delta), dto.getAmount());
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionOnDepositIfAccountIsAbsent() throws Exception {
        BigDecimal delta = BigDecimal.ONE;
        accountService.deposit(ABSENT_ACCOUNT_ID, delta);
    }

    @Test
    public void shouldWithdrawFromAccount() throws Exception {
        testAccount.setAmount(BigDecimal.TEN);
        BigDecimal beforeDeposit = testAccount.getAmount();
        BigDecimal delta = BigDecimal.ONE;
        AccountDto dto = accountService.withdraw(TEST_ACCOUNT_ID, delta);
        assertEquals(beforeDeposit.subtract(delta), dto.getAmount());
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionOnWithdrawIfAccountIsAbsent() throws Exception {
        BigDecimal delta = BigDecimal.ONE;
        accountService.withdraw(ABSENT_ACCOUNT_ID, delta);
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionOnWithdrawIfAmountIsNotEnough() throws Exception {
        testAccount.setAmount(BigDecimal.ONE);
        BigDecimal delta = BigDecimal.TEN;
        accountService.withdraw(ABSENT_ACCOUNT_ID, delta);
    }

    @Test
    public void shouldDelete() throws Exception {
        accountService.delete(TEST_ACCOUNT_ID);
    }

}