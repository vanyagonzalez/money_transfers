package com.piv.money.transfers.service.impl;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.dto.AccountDto;
import com.piv.money.transfers.resource.ApplicationException;
import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.service.AccountService;

import java.math.BigDecimal;

import static com.piv.money.transfers.converter.AccountDtoConverter.toDto;
import static com.piv.money.transfers.resource.ApplicationException.ACCOUNT_HAS_NOT_ENOUGH_AMOUNT;
import static com.piv.money.transfers.resource.ApplicationException.ACCOUNT_IS_ABSENT;

/**
 * Created by Ivan on 18.01.2020.
 */
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDto create(BigDecimal amount) {
        return toDto(accountDao.create(amount));
    }

    public AccountDto get(String id) {
        return toDto(accountDao.read(id));
    }

    public AccountDto deposit(String id, BigDecimal amount) throws ApplicationException {
        Account account = accountDao.read(id);
        if (account == null) {
            throw new ApplicationException(String.format(ACCOUNT_IS_ABSENT, id));
        }
        account.setAmount(account.getAmount().add(amount));
        return toDto(accountDao.updateAccount(account));
    }

    public AccountDto withdraw(String id, BigDecimal amount) throws ApplicationException {
        Account account = accountDao.read(id);
        if (account == null) {
            throw new ApplicationException(String.format(ACCOUNT_IS_ABSENT, id));
        }
        if (amount.compareTo(account.getAmount()) > 0) {
            throw new ApplicationException(String.format(ACCOUNT_HAS_NOT_ENOUGH_AMOUNT, id, account.getAmount(), amount));
        }
        account.setAmount(account.getAmount().subtract(amount));
        return toDto(accountDao.updateAccount(account));
    }

    public void delete(String id) {
        accountDao.delete(id);
    }
}
