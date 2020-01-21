package com.piv.money.transfers.service.impl;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.resource.ApplicationException;
import com.piv.money.transfers.model.Account;
import com.piv.money.transfers.service.TransferService;

import java.math.BigDecimal;

import static com.piv.money.transfers.resource.ApplicationException.ACCOUNT_HAS_NOT_ENOUGH_AMOUNT;

/**
 * Created by Ivan on 18.01.2020.
 */
public class TransferServiceImpl implements TransferService {
    private final AccountDao accountDao;

    public TransferServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void transfer(String fromId, String toId, BigDecimal amount) throws ApplicationException {
        try {
            accountDao.lock(fromId);
            accountDao.lock(toId);

            Account fromAccount = accountDao.read(fromId);

            if (amount.compareTo(fromAccount.getAmount()) > 0) {
                throw new ApplicationException(String.format(ACCOUNT_HAS_NOT_ENOUGH_AMOUNT, fromId, fromAccount.getAmount(), amount));
            }

            Account toAccount = accountDao.read(toId);

            fromAccount.setAmount(fromAccount.getAmount().subtract(amount));
            toAccount.setAmount(toAccount.getAmount().add(amount));

            accountDao.updateAccount(fromAccount);
            accountDao.updateAccount(toAccount);
        } finally {
            accountDao.unlock(fromId);
            accountDao.unlock(toId);
        }
    }
}
