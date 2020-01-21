package com.piv.money.transfers.model.impl.inmemory;

import com.piv.money.transfers.model.Account;

import java.math.BigDecimal;

/**
 * Created by Ivan on 18.01.2020.
 */
public class AccountInMemory extends BasicEntityInMemory implements Account {
    private BigDecimal amount;

    public AccountInMemory(String id, BigDecimal initialAmount) {
        super(id);
        this.amount = initialAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
