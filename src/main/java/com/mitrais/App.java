package com.mitrais;

import com.mitrais.config.FileDataSource;
import com.mitrais.model.TransactionHistory;
import com.mitrais.repository.*;
import com.mitrais.viewhandler.FrontController;
import com.mitrais.viewhandler.FrontControllerImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TransactionHistoryRepoFactory.setTransactionHistoryRepository(new TransactionHistoryRepositoryImpl());
        AccountRepository accountRepository = new AccountRepositoryImpl(new FileDataSource(), TransactionHistoryRepoFactory.getTransactionHistoryRepository());
        AccountRepoFactory.setAccountRepository(accountRepository);
        FrontController frontController = new FrontControllerImpl();
        frontController.goToView("WELCOME");
    }
}