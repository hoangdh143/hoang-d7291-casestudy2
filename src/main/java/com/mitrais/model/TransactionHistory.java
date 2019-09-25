package com.mitrais.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class TransactionHistory {
    private String transactionCode;
    private Account account;
    private String description;
    private Integer debit;
    private Integer credit;
    private Integer balance;
    LocalDateTime createdAt;

    public TransactionHistory(Account account, String description, Integer debit, Integer credit, Integer balance) {
        this.account = account;
        this.description = description;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "TransactionCode='" + transactionCode + '\'' +
                ", description='" + description + '\'' +
                ", debit=" + debit +
                ", credit=" + credit +
                ", balance=" + balance +
                ", createdAt=" + createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}