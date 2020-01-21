package com.piv.money.transfers.service.impl;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.resource.ApplicationException;
import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.service.TransferService;

import java.math.BigDecimal;

import static com.piv.money.transfers.resource.ApplicationException.ACCOUNT_HAS_NOT_ENOUGH_AMOUNT;
import static com.piv.money.transfers.resource.ApplicationException.ACCOUNT_IS_ABSENT;

/**
 * Created by Ivan on 18.01.2020.
 */
public class TransferServiceImpl implements TransferService {
    private final AccountDao accountDao;

    public TransferServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void transfer(String fromId, String toId, BigDecimal amount) throws ApplicationException {
        Account fromAccount = accountDao.read(fromId);
        if (fromAccount == null) {
            throw new ApplicationException(String.format(ACCOUNT_IS_ABSENT, fromId));
        }
        if (amount.compareTo(fromAccount.getAmount()) > 0) {
            throw new ApplicationException(String.format(ACCOUNT_HAS_NOT_ENOUGH_AMOUNT, fromId, fromAccount.getAmount(), amount));
        }

        Account toAccount = accountDao.read(toId);
        if (toAccount == null) {
            throw new ApplicationException(String.format(ACCOUNT_IS_ABSENT, toId));
        }

        fromAccount.setAmount(fromAccount.getAmount().subtract(amount));
        accountDao.updateAccount(fromAccount);
        toAccount.setAmount(toAccount.getAmount().add(amount));
        accountDao.updateAccount(toAccount);
    }
}
