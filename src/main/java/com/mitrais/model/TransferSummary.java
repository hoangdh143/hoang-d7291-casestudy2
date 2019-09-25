package com.mitrais.model;

import lombok.Data;

@Data
public class TransferSummary {
    private String destinationAccount;
    private Integer transferAmount;
    private String referenceNumber;
    private Integer balance;

    public TransferSummary(String destinationAccount, Integer transferAmount, String referenceNumber, Integer balance) {
        this.destinationAccount = destinationAccount;
        this.transferAmount = transferAmount;
        this.referenceNumber = referenceNumber;
        this.balance = balance;
    }
}
