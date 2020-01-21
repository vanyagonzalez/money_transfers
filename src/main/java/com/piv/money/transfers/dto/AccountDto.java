package com.piv.money.transfers.dto;

import java.math.BigDecimal;

/**
 * Created by Ivan on 18.01.2020.
 */
public class AccountDto {
    private String id;
    private BigDecimal amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
