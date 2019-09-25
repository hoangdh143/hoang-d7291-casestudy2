package com.mitrais.viewhandler;

import com.mitrais.model.Account;
import com.mitrais.model.TransactionSummary;
import com.mitrais.model.TransferConfirmation;
import com.mitrais.model.TransferSummary;

public interface Dispatcher {
    void dispatch(String viewName);

    Account getAccount();
    void setAccount(Account account);

    TransactionSummary getTransactionSummary();
    void setTransactionSummary(TransactionSummary transactionSummary);

    TransferConfirmation getTransferConfirmation();
    void setTransferConfirmation(TransferConfirmation transferConfirmation);

    TransferSummary getTransferSummary();
    void setTransferSummary(TransferSummary transferSummary);
}
