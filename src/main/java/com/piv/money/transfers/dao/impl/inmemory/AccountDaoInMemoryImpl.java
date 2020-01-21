package com.piv.money.transfers.dao.impl.inmemory;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.dao.impl.inmemory.datasource.InMemoryDataSource;
import com.piv.money.transfers.model.Account;

import java.math.BigDecimal;

/**
 * Created by Ivan on 18.01.2020.
 */
public class AccountDaoInMemoryImpl implements AccountDao {
    private final InMemoryDataSource dataSource;

    public AccountDaoInMemoryImpl(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Account create(BigDecimal initialValue) {
        return dataSource.createNewAccount(initialValue);
    }

    public Account read(String id) {
        return dataSource.readAccount(id);
    }

    public Account updateAccount(Account account) {
        return dataSource.updateAccount(account);
    }

    public Account lock(String id) {
        Account account = read(id);
        account.setLocked(true);
        return dataSource.updateAccount(account);
    }

    public Account unlock(String id) {
        Account account = read(id);
        account.setLocked(false);
        return dataSource.updateAccount(account);
    }

    public void delete(String id) {
        dataSource.deleteAccount(id);
    }
}
