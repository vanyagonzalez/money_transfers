package com.piv.money.transfers.converter;

import com.piv.money.transfers.dto.AccountDto;
import com.piv.money.transfers.model.Account;

/**
 * Created by Ivan on 19.01.2020.
 */
public class AccountDtoConverter {
    public static AccountDto toDto(Account entity) {
        if (entity == null) {
            return null;
        }
        AccountDto dto = new AccountDto();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        return dto;
    }
}
