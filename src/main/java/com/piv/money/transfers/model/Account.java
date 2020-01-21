package com.piv.money.transfers.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Ivan on 18.01.2020.
 */
public interface Account extends BasicEntity {

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

}
