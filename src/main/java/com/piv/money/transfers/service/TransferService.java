package com.piv.money.transfers.service;

import com.piv.money.transfers.resource.ApplicationException;

import java.math.BigDecimal;

/**
 * Created by Ivan on 18.01.2020.
 */
public interface TransferService {
    void transfer(String fromId, String toId, BigDecimal amount) throws ApplicationException;
}
