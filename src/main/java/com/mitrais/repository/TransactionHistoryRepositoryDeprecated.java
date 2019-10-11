package com.mitrais.repository;

import com.mitrais.model.TransactionHistory;

import java.util.List;

public interface TransactionHistoryRepositoryDeprecated {
    void save(TransactionHistory transactionHistory);
    List<TransactionHistory> findByAccountNumber(String accountNumber, int page, int limit);
}
