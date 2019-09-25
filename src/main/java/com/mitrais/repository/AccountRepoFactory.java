package com.mitrais.repository;

public class AccountRepoFactory {
    private static AccountRepository accountRepository;
    public static void setAccountRepository(AccountRepository accountRepository) {
        AccountRepoFactory.accountRepository = accountRepository;
    }

    public static AccountRepository getAccountRepository() {
        return AccountRepoFactory.accountRepository;
    }
}
