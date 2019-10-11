package com.mitrais.repository;

import com.mitrais.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String> {
    List<TransactionHistory> findAllByAccount_AccountNumber(String accountNumber);
}
