package com.mitrais.repository;

import com.mitrais.model.TransactionHistory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionHistoryRepositoryDeprecatedImpl implements TransactionHistoryRepositoryDeprecated {
    private static Map<String, TransactionHistory> transactionHistoryMap = new ConcurrentHashMap<>();

    @Override
    public void save(TransactionHistory transactionHistory) {
//        if (transactionHistory.getTransactionCode() == null) {
//            transactionHistory.setTransactionCode(UUID.randomUUID().toString());
//        }
//        transactionHistoryMap.put(transactionHistory.getTransactionCode(), transactionHistory);
    }

    @Override
    public List<TransactionHistory> findByAccountNumber(String accountNumber, int page, int limit) {
        List<TransactionHistory> transactionHistoryList = transactionHistoryMap.values().stream()
                .filter(transactionHistory -> transactionHistory.getAccount().getAccountNumber().equals(accountNumber))
                .sorted(Comparator.comparing(TransactionHistory::getCreatedAt).reversed())
                .skip(page*limit).limit(limit).collect(Collectors.toList());
        return transactionHistoryList;
    }
}
