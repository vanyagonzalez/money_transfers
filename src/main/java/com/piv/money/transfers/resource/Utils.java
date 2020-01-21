package com.piv.money.transfers.resource;

import java.math.BigDecimal;

import static com.piv.money.transfers.resource.ApplicationException.AMOUNT_IS_LESS_THAN_ZERO;
import static com.piv.money.transfers.resource.ApplicationException.AMOUNT_IS_NOT_A_NUMBER;

/**
 * Created by Ivan on 21.01.2020.
 */
public class Utils {
    public static BigDecimal prepareAmount(String amount) throws ApplicationException {
        if (amount == null || "".equals(amount.trim())) {
            return BigDecimal.ZERO;
        } else {
            try {
                BigDecimal initialValue = new BigDecimal(amount);
                if (initialValue.compareTo(BigDecimal.ZERO) < 0) {
                    throw new ApplicationException(String.format(AMOUNT_IS_LESS_THAN_ZERO, initialValue));
                }
                return initialValue;
            } catch (NumberFormatException e) {
                throw new ApplicationException(String.format(AMOUNT_IS_NOT_A_NUMBER, amount));
            }
        }
    }
}

