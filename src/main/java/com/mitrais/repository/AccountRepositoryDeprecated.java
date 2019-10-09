package com.mitrais.repository;

import com.mitrais.exception.*;
import com.mitrais.model.Account;
import com.mitrais.model.TransactionSummary;
import com.mitrais.model.TransferSummary;

public interface AccountRepositoryDeprecated {
    Account save(Account account);
    Account update(Account account);
    Account get(String accountNumber, String pin);

    TransactionSummary deduct(Account account, int i) throws BalanceInsufficientException, MaximumAmountException, InvalidAmountException, InvalidAccountException;

    TransferSummary transfer(String accountNumber, String destinationAccount, String transferAmount, String refNumber) throws
            InvalidAccountException, MaximumAmountException, MinimumAmountException, InvalidAmountException,
            BalanceInsufficientException, InvalidRefNumberException;
}