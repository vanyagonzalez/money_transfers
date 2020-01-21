package com.piv.money.transfers;

import com.piv.money.transfers.dao.AccountDao;
import com.piv.money.transfers.dao.impl.inmemory.AccountDaoInMemoryImpl;
import com.piv.money.transfers.dao.impl.inmemory.datasource.InMemoryDataSource;
import com.piv.money.transfers.resource.AccountResource;
import com.piv.money.transfers.service.AccountService;
import com.piv.money.transfers.service.TransferService;
import com.piv.money.transfers.service.impl.AccountServiceImpl;
import com.piv.money.transfers.service.impl.TransferServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Ivan on 19.01.2020.
 */
public class MoneyTransferApplication extends ResourceConfig {
    public MoneyTransferApplication() {
        register(new AbstractBinder() {
            protected void configure() {
                AccountDao accountDao = new AccountDaoInMemoryImpl(new InMemoryDataSource());
                bind(new AccountServiceImpl(accountDao)).to(AccountService.class);
                bind(new TransferServiceImpl(accountDao)).to(TransferService.class);
            }
        });

        packages(AccountResource.class.getPackage().getName());
    }
}
