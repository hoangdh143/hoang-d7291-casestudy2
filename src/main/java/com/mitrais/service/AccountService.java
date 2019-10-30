package com.mitrais.service;

import com.mitrais.exception.*;
import com.mitrais.model.Account;
import com.mitrais.model.TransactionSummary;
import com.mitrais.model.TransferSummary;

import java.io.InputStream;

public interface AccountService {
    Account authenticate(String accountNumber, String pin) throws InvalidAccountException;

    TransactionSummary deduct(String accountNumber, int i) throws BalanceInsufficientException, MaximumAmountException, InvalidAmountException, InvalidAccountException;

    TransferSummary transfer(String accountNumber, String destinationAccount, Integer transferAmount, String refNumber) throws
            InvalidAccountException, MaximumAmountException, MinimumAmountException, InvalidAmountException,
            BalanceInsufficientException, InvalidRefNumberException;

    void importFromFile(String filePath) throws DataSourceException;
    void importFromFile (InputStream inputStream) throws DataSourceException;
}