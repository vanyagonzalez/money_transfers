package com.piv.money.transfers.dao.impl.inmemory;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.dao.impl.inmemory.datasource.InMemoryDataSource;
import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.model.impl.inmemory.AccountInMemory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by Ivan on 18.01.2020.
 */
public class AccountDaoInMemoryImplTest {
    private static final String TEST_ACCOUNT_ID = "123";
    private static AccountInMemory testAccount;
    private static AccountDao accountDao;

    @BeforeClass
    public static void setup() throws Exception {
        InMemoryDataSource dataSource = Mockito.mock(InMemoryDataSource.class);
        testAccount = new AccountInMemory(TEST_ACCOUNT_ID, BigDecimal.TEN);
        when(dataSource.createNewAccount(any(BigDecimal.class))).thenReturn(testAccount);
        when(dataSource.readAccount(any(String.class))).thenReturn(testAccount);
        when(dataSource.updateAccount(any(AccountInMemory.class))).thenReturn(testAccount);
        doNothing().when(dataSource).deleteAccount(any(String.class));

        accountDao = new AccountDaoInMemoryImpl(dataSource);
    }

    @Test
    public void shouldCreateAccountWithInitialValue() throws Exception {
        Account created = accountDao.create(BigDecimal.ONE);
        assertNotNull(created);
        assertEquals(testAccount.getId(), created.getId());
        assertEquals(testAccount.getAmount(), created.getAmount());
    }

    @Test
    public void shouldReturnAccountById() throws Exception {
        Account returned = accountDao.read(UUID.randomUUID().toString());
        assertNotNull(returned);
        assertEquals(testAccount.getId(), returned.getId());
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        Account updatedAccount = accountDao.updateAccount(testAccount);
        assertNotNull(updatedAccount);
        assertEquals(testAccount.getId(), updatedAccount.getId());
        assertEquals(testAccount.getAmount(), updatedAccount.getAmount());
    }

    @Test
    public void shouldLockAccount() throws Exception {
        testAccount.setLocked(false);
        Account lockedAccount = accountDao.lock(UUID.randomUUID().toString());
        assertNotNull(lockedAccount);
        assertTrue(lockedAccount.isLocked());

    }

    @Test
    public void shouldUnlockAccount() throws Exception {
        testAccount.setLocked(true);
        Account lockedAccount = accountDao.unlock(UUID.randomUUID().toString());
        assertNotNull(lockedAccount);
        assertFalse(lockedAccount.isLocked());
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        accountDao.delete(UUID.randomUUID().toString());
    }
}