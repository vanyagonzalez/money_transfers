package com.piv.money.transfers.service;

import com.piv.money.transfers.dto.AccountDto;
import com.piv.money.transfers.resource.ApplicationException;

import java.math.BigDecimal;

/**
 * Created by Ivan on 18.01.2020.
 */
public interface AccountService {
    AccountDto create(BigDecimal amount);
    AccountDto get(String id);
    AccountDto deposit(String id, BigDecimal amount) throws ApplicationException;
    AccountDto withdraw(String id, BigDecimal amount) throws ApplicationException;
    void delete(String id);
}
