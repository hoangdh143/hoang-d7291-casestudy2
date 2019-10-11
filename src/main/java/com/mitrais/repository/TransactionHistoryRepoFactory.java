package com.mitrais.repository;

public class TransactionHistoryRepoFactory {
    private static TransactionHistoryRepositoryDeprecated TransactionHistoryRepositoryDeprecated;
    public static void setTransactionHistoryRepositoryDeprecated(TransactionHistoryRepositoryDeprecated TransactionHistoryRepositoryDeprecated) {
        TransactionHistoryRepoFactory.TransactionHistoryRepositoryDeprecated = TransactionHistoryRepositoryDeprecated;
    }

    public static TransactionHistoryRepositoryDeprecated getTransactionHistoryRepositoryDeprecated() {
        return TransactionHistoryRepoFactory.TransactionHistoryRepositoryDeprecated;
    }
}
