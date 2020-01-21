package com.piv.money.transfers.service.impl;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.dto.AccountDto;
import com.piv.money.transfers.resource.ApplicationException;
import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.model.impl.inmemory.AccountInMemory;
import com.piv.money.transfers.service.AccountService;
import com.piv.money.transfers.service.TransferService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static com.piv.money.transfers.UtilsTest.ABSENT_ACCOUNT_ID;
import static com.piv.money.transfers.UtilsTest.TEST_ACCOUNT_ID_1;
import static com.piv.money.transfers.UtilsTest.TEST_ACCOUNT_ID_2;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by Ivan on 18.01.2020.
 */
public class TransferServiceImplTest {
    private static Account testAccount1;
    private static Account testAccount2;
    private static AccountService accountService;
    private static TransferService transferService;

    @BeforeClass
    public static void setup() throws Exception {
        AccountDao accountDao = Mockito.mock(AccountDao.class);
        testAccount1 = new AccountInMemory(TEST_ACCOUNT_ID_1, BigDecimal.TEN);
        testAccount2 = new AccountInMemory(TEST_ACCOUNT_ID_2, BigDecimal.TEN);
        when(accountDao.read(TEST_ACCOUNT_ID_1)).thenReturn(testAccount1);
        when(accountDao.read(TEST_ACCOUNT_ID_2)).thenReturn(testAccount2);
        when(accountDao.read(ABSENT_ACCOUNT_ID)).thenReturn(null);
        when(accountDao.lock(ABSENT_ACCOUNT_ID)).thenThrow(ApplicationException.class);
        when(accountDao.updateAccount(testAccount1)).thenReturn(testAccount1);
        when(accountDao.updateAccount(testAccount2)).thenReturn(testAccount1);
        doNothing().when(accountDao).delete(any(String.class));

        accountService = new AccountServiceImpl(accountDao);
        transferService = new TransferServiceImpl(accountDao);
    }

    @Test
    public void shouldTransferAmountBetweenAccount() throws Exception {
        BigDecimal initialAmount = BigDecimal.TEN;
        BigDecimal delta = BigDecimal.ONE;
        testAccount1.setAmount(initialAmount);
        testAccount2.setAmount(initialAmount);
        transferService.transfer(TEST_ACCOUNT_ID_1, TEST_ACCOUNT_ID_2, delta);

        AccountDto accountDto1 = accountService.get(TEST_ACCOUNT_ID_1);
        AccountDto accountDto2 = accountService.get(TEST_ACCOUNT_ID_2);

        assertEquals(initialAmount.subtract(delta), accountDto1.getAmount());
        assertEquals(initialAmount.add(delta), accountDto2.getAmount());
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionIfFromAccountIsAbsent() throws Exception {
        BigDecimal delta = BigDecimal.ONE;
        transferService.transfer(ABSENT_ACCOUNT_ID, TEST_ACCOUNT_ID_2, delta);
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionIfToAccountIsAbsent() throws Exception {
        BigDecimal delta = BigDecimal.ONE;
        transferService.transfer(TEST_ACCOUNT_ID_1, ABSENT_ACCOUNT_ID, delta);
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionIfAmountIsNotEnough() throws Exception {
        testAccount1.setAmount(BigDecimal.ZERO);
        testAccount2.setAmount(BigDecimal.TEN);
        BigDecimal delta = BigDecimal.ONE;
        transferService.transfer(TEST_ACCOUNT_ID_1, TEST_ACCOUNT_ID_2, delta);
    }
}