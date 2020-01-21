package com.piv.money.transfers.dao;

import com.piv.money.transfers.model.Account;

import java.math.BigDecimal;

/**
 * Created by Ivan on 18.01.2020.
 */
public interface AccountDao {
    Account create(BigDecimal initialValue);
    Account read(String id);
    Account updateAccount(Account account);
    Account lock(String id);
    Account unlock(String id);
    void delete(String id);
}
