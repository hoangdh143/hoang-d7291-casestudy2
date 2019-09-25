package com.mitrais.viewhandler;

import com.mitrais.model.Account;
import com.mitrais.model.TransactionSummary;
import com.mitrais.model.TransferConfirmation;
import com.mitrais.model.TransferSummary;
import com.mitrais.view.View;

import java.util.*;
import java.util.stream.Collectors;

public class DispatcherImpl implements Dispatcher {
    private Map<String, View> viewMap = Arrays.stream(ViewName.values())
            .peek(e -> e.getView().setDispatcher(this))
            .collect(Collectors.toMap(Enum::name, ViewName::getView));

    private Account account;
    private TransactionSummary transactionSummary;
    private TransferConfirmation transferConfirmation;
    private TransferSummary transferSummary;

    @Override
    public void dispatch(String viewName) {
        View view = viewMap.get(viewName);
        if (view == null) return;
        view.display();
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public TransactionSummary getTransactionSummary() {
        return transactionSummary;
    }

    @Override
    public void setTransactionSummary(TransactionSummary transactionSummary) {
        this.transactionSummary = transactionSummary;
    }

    @Override
    public TransferConfirmation getTransferConfirmation() {
        return transferConfirmation;
    }

    @Override
    public void setTransferConfirmation(TransferConfirmation transferConfirmation) {
        this.transferConfirmation = transferConfirmation;
    }

    @Override
    public TransferSummary getTransferSummary() {
        return transferSummary;
    }

    @Override
    public void setTransferSummary(TransferSummary transferSummary) {
        this.transferSummary = transferSummary;
    }
}