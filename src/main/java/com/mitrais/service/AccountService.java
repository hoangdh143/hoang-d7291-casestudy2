package com.mitrais.service;

import com.mitrais.exception.*;
import com.mitrais.model.Account;
import com.mitrais.model.TransactionSummary;
import com.mitrais.model.TransferSummary;

public interface AccountService {
    Account validate(String accountNumber, String pin) throws InvalidAccountException;

    TransactionSummary deduct(Account account, int i) throws BalanceInsufficientException, MaximumAmountException, InvalidAmountException, InvalidAccountException;

    TransferSummary transfer(String accountNumber, String destinationAccount, String transferAmount, String refNumber) throws
            InvalidAccountException, MaximumAmountException, MinimumAmountException, InvalidAmountException,
            BalanceInsufficientException, InvalidRefNumberException;
}