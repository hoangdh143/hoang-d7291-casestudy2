package com.mitrais.service.impl;

import com.mitrais.config.ExternalDataSource;
import com.mitrais.config.FileExternalDataSource;
import com.mitrais.config.FileInputStreamDataSource;
import com.mitrais.exception.*;
import com.mitrais.model.Account;
import com.mitrais.model.TransactionHistory;
import com.mitrais.model.TransactionSummary;
import com.mitrais.model.TransferSummary;
import com.mitrais.repository.AccountRepository;
import com.mitrais.repository.TransactionHistoryRepository;
import com.mitrais.service.AccountService;
import com.mitrais.validator.AccountValidationContext;
import com.mitrais.validator.AccountValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.accountRepository = accountRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Override
    public void importFromFile(String filePath) throws DataSourceException {
        ExternalDataSource<Account> externalDataSource = new FileExternalDataSource(filePath);
        externalDataSource.saveToRepo(accountRepository);
    }

    @Override
    public void importFromFile(InputStream inputStream) throws DataSourceException {
        ExternalDataSource<Account> externalDataSource = new FileInputStreamDataSource(inputStream);
        externalDataSource.saveToRepo(accountRepository);
    }

    @Override
    public Account authenticate(String accountNumber, String pin) throws InvalidAccountException {
        AccountValidationContext context = new AccountValidationContext();
        context.addStrategy(AccountValidationStrategy.ACCOUNT_NUMBER);
        context.addStrategy(AccountValidationStrategy.PIN);


        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setPin(pin);

        String errorCode = context.execute(account);
        if (errorCode != null) throw new InvalidAccountException(errorCode);

        Optional<Account> accountDb = accountRepository.findByAccountNumberAndPin(accountNumber, pin);
        if (!accountDb.isPresent()) throw new InvalidAccountException("Incorrect Account Number or PIN");

        return accountDb.get();
    }

    @Override
    public TransactionSummary deduct(String accountNumber, int amount) throws BalanceInsufficientException, MaximumAmountException, InvalidAmountException, InvalidAccountException {
        if (amount > 1000) throw new MaximumAmountException("Maximum amount to withdraw is $1000");
        if (amount % 10 != 0) throw new InvalidAmountException("Ammount must be a multiple of 10");
        Account accoundDb = accountRepository.findByAccountNumber(accountNumber);
        if (accoundDb != null) {
            if (amount > accoundDb.getBalance()) throw new BalanceInsufficientException("Insufficient balance $" + amount);
            accoundDb.setBalance(accoundDb.getBalance() - amount);
            accountRepository.save(accoundDb);

            transactionHistoryRepository.save(new TransactionHistory(accoundDb, "Withdraw transaction", amount, 0, accoundDb.getBalance()));

            return new TransactionSummary(new Date(), amount, accoundDb.getBalance());
        } else {
            throw new InvalidAccountException("Invalid account");
        }
    }

    @Override
    public TransferSummary transfer(String accountNumber, String destinationAccount, Integer amount, String refNumber) throws InvalidAccountException, MaximumAmountException, MinimumAmountException, InvalidAmountException, BalanceInsufficientException, InvalidRefNumberException {
        try {Integer.parseInt(accountNumber);} catch (NumberFormatException e) {throw new InvalidAccountException("Invalid account");}
        try {Integer.parseInt(destinationAccount);} catch (NumberFormatException e) {throw new InvalidAccountException("Invalid account");}
        if (accountNumber.equals(destinationAccount)) throw new InvalidAccountException("Invalid account");

        Account sourceAccount = accountRepository.findByAccountNumber(accountNumber);
        Account destAccount = accountRepository.findByAccountNumber(destinationAccount);

        if (sourceAccount == null) throw new InvalidAccountException("Invalid account");
        if (destAccount == null) throw new InvalidAccountException("Invalid account");

        if (amount > 1000) throw new MaximumAmountException("Maximum amount to withdraw is $1000");
        if (amount < 0) throw new MinimumAmountException("Minimum amount to withdraw is $1");
        if (amount > sourceAccount.getBalance()) throw new BalanceInsufficientException("Insufficient balance $" + amount);
        try {Integer.parseInt(refNumber);} catch (NumberFormatException e) {throw new InvalidRefNumberException("Invalid Reference Number");}

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destAccount.setBalance(destAccount.getBalance() + amount);
        accountRepository.save(sourceAccount);
        accountRepository.save(destAccount);

        transactionHistoryRepository.save(new TransactionHistory(sourceAccount, "Transfer transaction", amount, 0, sourceAccount.getBalance()));
        transactionHistoryRepository.save(new TransactionHistory(destAccount, "Received from transfer transaction", 0, amount, destAccount.getBalance()));

        return new TransferSummary(destinationAccount, amount, refNumber, sourceAccount.getBalance());
    }
}
