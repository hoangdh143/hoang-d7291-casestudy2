package com.mitrais.repository;

public class TransactionHistoryRepoFactory {
    private static TransactionHistoryRepository TransactionHistoryRepository;
    public static void setTransactionHistoryRepository(TransactionHistoryRepository TransactionHistoryRepository) {
        TransactionHistoryRepoFactory.TransactionHistoryRepository = TransactionHistoryRepository;
    }

    public static TransactionHistoryRepository getTransactionHistoryRepository() {
        return TransactionHistoryRepoFactory.TransactionHistoryRepository;
    }
}
