package com.piv.money.transfers.dao.impl.inmemory.datasource;

import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.model.impl.inmemory.AccountInMemory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ivan on 18.01.2020.
 */
public class InMemoryDataSource {
    private final Map<String, AccountInMemory> accounts;

    public InMemoryDataSource() {
        this.accounts = new ConcurrentHashMap<String, AccountInMemory>();
    }

    public AccountInMemory createNewAccount(BigDecimal initialAmount) {
        String uuid = UUID.randomUUID().toString();
        AccountInMemory account = new AccountInMemory(uuid, initialAmount);
        accounts.put(uuid, account);
        return account.clone();
    }

    public AccountInMemory readAccount(String id) {
        AccountInMemory accountInMemory = accounts.get(id);
        return accountInMemory != null ? accountInMemory.clone() : null;
    }

    public Account updateAccount(AccountInMemory account) {
        AccountInMemory accountInMap = accounts.get(account.getId());
        if(accountInMap == null) {
            throw new RuntimeException(String.format("Account [%s] was deleted", account.getId()));
        }
        synchronized (accountInMap) {
            if (accounts.get(account.getId()) == null) {
                throw new RuntimeException(String.format("Account [%s] was deleted", account.getId()));
            }
            if (!accountInMap.getLastChangeDate().equals(account.getLastChangeDate())) {
                throw new RuntimeException(String.format("Account [%s] was changed", account.getId()));
            }
            accountInMap.setAmount(account.getAmount());
            accountInMap.setLocked(account.isLocked());
            accountInMap.setLastChangeDate(new Date());
        }
        return account;
    }

    public void deleteAccount(String id) {
        accounts.remove(id);
    }
}
