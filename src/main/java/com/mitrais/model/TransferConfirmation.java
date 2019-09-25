package com.mitrais.model;

import lombok.Data;

@Data
public class TransferConfirmation {
    private Account account;
    private String destinationAccount;
    private String transferAmount;
    private String referenceNumber;

    public TransferConfirmation(Account account, String destinationAccount, String referenceNumber) {
        this.account = account;
        this.destinationAccount = destinationAccount;
        this.referenceNumber = referenceNumber;
    }
}
