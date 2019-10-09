package com.mitrais.repository;

public class AccountRepoFactory {
    private static AccountRepositoryDeprecated accountRepositoryDeprecated;
    public static void setAccountRepositoryDeprecated(AccountRepositoryDeprecated accountRepositoryDeprecated) {
        AccountRepoFactory.accountRepositoryDeprecated = accountRepositoryDeprecated;
    }

    public static AccountRepositoryDeprecated getAccountRepositoryDeprecated() {
        return AccountRepoFactory.accountRepositoryDeprecated;
    }
}
