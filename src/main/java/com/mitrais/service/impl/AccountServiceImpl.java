package com.mitrais.service.impl;

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

import java.util.Date;
import java.util.List;
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
    public Account validate(String accountNumber, String pin) throws InvalidAccountException {
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
    public TransactionSummary deduct(Account account, int i) throws BalanceInsufficientException, MaximumAmountException, InvalidAmountException, InvalidAccountException {
        if (i > 1000) throw new MaximumAmountException("Maximum amount to withdraw is $1000");
        if (i % 10 != 0) throw new InvalidAmountException("Invalid ammount");
        Account accoundDb = accountRepository.findByAccountNumber(account.getAccountNumber());
        if (accoundDb != null) {
            if (i > accoundDb.getBalance()) throw new BalanceInsufficientException("Insufficient balance $" + i);
            accoundDb.setBalance(accoundDb.getBalance() - i);
            accountRepository.save(accoundDb);

            transactionHistoryRepository.save(new TransactionHistory(accoundDb, "Withdraw transaction", i, 0, accoundDb.getBalance()));

            return new TransactionSummary(new Date(), i, accoundDb.getBalance());
        } else {
            throw new InvalidAccountException("Invalid account");
        }
    }

    @Override
    public TransferSummary transfer(String accountNumber, String destinationAccount, String transferAmount, String refNumber) throws InvalidAccountException, MaximumAmountException, MinimumAmountException, InvalidAmountException, BalanceInsufficientException, InvalidRefNumberException {
        try {Integer.parseInt(accountNumber);} catch (NumberFormatException e) {throw new InvalidAccountException("Invalid account");}
        try {Integer.parseInt(destinationAccount);} catch (NumberFormatException e) {throw new InvalidAccountException("Invalid account");}
        if (accountNumber.equals(destinationAccount)) throw new InvalidAccountException("Invalid account");

        Account sourceAccount = accountRepository.findByAccountNumber(accountNumber);
        Account destAccount = accountRepository.findByAccountNumber(destinationAccount);

        if (sourceAccount == null) throw new InvalidAccountException("Invalid account");
        if (destAccount == null) throw new InvalidAccountException("Invalid account");

        int amount;
        try {amount = Integer.parseInt(transferAmount);} catch (NumberFormatException e) {throw new InvalidAccountException("Invalid amount");}
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
