package com.mitrais.repository;

import com.mitrais.model.TransactionHistory;

import java.util.List;

public interface TransactionHistoryRepository {
    void save(TransactionHistory transactionHistory);
    List<TransactionHistory> findByAccountNumber(String accountNumber, int page, int limit);
}
