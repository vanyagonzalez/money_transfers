package com.piv.money.transfers.dao.impl.inmemory.datasource;

import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.model.impl.inmemory.AccountInMemory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ivan on 18.01.2020.
 */
public class InMemoryDataSource {
    private final Map<String, Account> accounts;

    public InMemoryDataSource() {
        this.accounts = new HashMap<String, Account>();
    }

    public Account createNewAccount(BigDecimal initialAmount) {
        String uuid = UUID.randomUUID().toString();
        Account account = new AccountInMemory(uuid, initialAmount);
        accounts.put(uuid, account);
        return account;
    }

    public Account readAccount(String id) {
        return accounts.get(id);
    }

    public Account updateAccount(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    public void deleteAccount(String id) {
        accounts.put(id, null);
    }
}
