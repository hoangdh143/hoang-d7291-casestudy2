package com.mitrais.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionCode;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Length(max = 500)
    private String description;

    private Integer debit;

    private Integer credit;

    private Integer balance;

    LocalDateTime createdAt;

    public TransactionHistory() {
    }

    public TransactionHistory(Account account, String description, Integer debit, Integer credit, Integer balance, LocalDateTime createdAt) {
        this.account = account;
        this.description = description;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.createdAt = createdAt;
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