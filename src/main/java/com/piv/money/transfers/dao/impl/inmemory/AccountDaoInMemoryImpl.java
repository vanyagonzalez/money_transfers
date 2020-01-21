package com.piv.money.transfers.dao.impl.inmemory;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.dao.impl.inmemory.datasource.InMemoryDataSource;
import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.model.impl.inmemory.AccountInMemory;
import com.piv.money.transfers.resource.ApplicationException;

import java.math.BigDecimal;

import static com.piv.money.transfers.resource.ApplicationException.ACCOUNT_IS_ABSENT;
import static com.piv.money.transfers.resource.ApplicationException.ACCOUNT_IS_LOCKED;

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
        return dataSource.updateAccount((AccountInMemory)account);
    }

    public Account lock(String id) throws ApplicationException {
        Account account = read(id);
        if (account == null) {
            throw new ApplicationException(String.format(ACCOUNT_IS_ABSENT, id));
        }
        if (account.isLocked()) {
            throw new ApplicationException(String.format(ACCOUNT_IS_LOCKED, id));
        }
        account.setLocked(true);
        return dataSource.updateAccount((AccountInMemory)account);
    }

    public Account unlock(String id) {
        Account account = read(id);
        if (account != null) {
            account.setLocked(false);
            return dataSource.updateAccount((AccountInMemory) account);
        } else {
            return null;
        }
    }

    public void delete(String id) {
        dataSource.deleteAccount(id);
    }
}
