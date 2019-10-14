package com.mitrais.serviceTest;

import com.mitrais.config.DataSourceConfig;
import com.mitrais.exception.*;
import com.mitrais.model.Account;
import com.mitrais.model.TransactionHistory;
import com.mitrais.repository.AccountRepository;
import com.mitrais.repository.TransactionHistoryRepository;
import com.mitrais.service.AccountService;
import com.mitrais.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {
        DataSourceConfig.class})
@ActiveProfiles("test")
@Transactional
public class AccountServiceTest {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void WhenImportFromFileShouldReturnAccount() throws Exception {
        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.importFromFile("Accounts.csv");
        Account accountDb = accountRepository.findByAccountNumber("353367");

        assertThat(accountDb.getName(), equalToIgnoringCase("Susann Mitham"));
    }

    @Test
    public void WhenImportFromFileShouldReturnAList() throws Exception {
        int ACCOUNT_LIST_SIZE = 96;
        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.importFromFile("Accounts.csv");
        List<Account> accountDb = accountRepository.findAll();

        Assert.assertEquals(accountDb.size(), ACCOUNT_LIST_SIZE);
    }

    @Test
    public void WhenWithdrawAccountBalanceWillBeReduced() throws Exception {
        Account account = new Account("Will Smith", "232432", 120, "121321" );
        Account accountDb = accountRepository.save(account);
        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.deduct(accountDb.getAccountNumber(), 20);
        accountDb = accountRepository.findByAccountNumber(accountDb.getAccountNumber());

        Assert.assertEquals(accountDb.getBalance().intValue(), 100);

    }

    @Test(expected = BalanceInsufficientException.class)
    public void WhenBalanceNotEnoughReturnError() throws BalanceInsufficientException, InvalidAmountException, MaximumAmountException, InvalidAccountException {
        Account account = new Account("Will Smith", "232432", 20, "121321" );
        Account accountDb = accountRepository.save(account);
        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.deduct(accountDb.getAccountNumber(), 50);
    }

    @Test
    public void WhenTransferTheDestinationAccountWillReceive() throws Exception {
        Account srcAccount = new Account("Will Smith", "232432", 220, "121321" );
        Account destAccount = new Account("John Wellington", "232432", 20, "153256" );
        accountRepository.save(srcAccount);
        accountRepository.save(destAccount);

        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.transfer(srcAccount.getAccountNumber(), destAccount.getAccountNumber(), 80, "121456");
        srcAccount = accountRepository.findByAccountNumber(srcAccount.getAccountNumber());
        destAccount = accountRepository.findByAccountNumber(destAccount.getAccountNumber());

        Assert.assertEquals(srcAccount.getBalance().intValue(), 140);
        Assert.assertEquals(destAccount.getBalance().intValue(), 100);
    }

    @Test(expected = BalanceInsufficientException.class)
    public void WhenTransferExceededBalanceWillThrowException() throws Exception {
        Account srcAccount = new Account("Will Smith", "232432", 20, "121321" );
        Account destAccount = new Account("John Wellington", "232432", 20, "153256" );
        accountRepository.save(srcAccount);
        accountRepository.save(destAccount);

        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.transfer(srcAccount.getAccountNumber(), destAccount.getAccountNumber(), 100, "121456");

    }

    @Test(expected = InvalidAccountException.class)
    public void WhenDestinationAccountNotExistThrowException() throws Exception {
        Account srcAccount = new Account("Will Smith", "232432", 220, "121321" );
        accountRepository.save(srcAccount);

        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.transfer(srcAccount.getAccountNumber(), "214568", 100, "121456");

    }

    @Test
    public void WhenMake1TransactionWillBeRecordedIn10LastestTransactions() throws Exception {
        Account account = new Account("Will Smith", "232432", 220, "121321" );
        accountRepository.save(account);
        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.deduct(account.getAccountNumber(), 20);
        Pageable page = PageRequest.of(0, 10);
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAllByAccountNumber(account.getAccountNumber(), page);

        Assert.assertTrue(transactionHistoryList.stream().anyMatch(x -> x.getDebit() == 20));

    }

    @Test
    public void WhenMakeMultipleTransactionsWillBeRecordedIn10LastestTransactions() throws Exception {
        Account account = new Account("Will Smith", "232432", 220, "121321" );
        accountRepository.save(account);
        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        accountService.deduct(account.getAccountNumber(), 20);
        accountService.deduct(account.getAccountNumber(), 10);
        accountService.deduct(account.getAccountNumber(), 30);

        Pageable page = PageRequest.of(0, 10);
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAllByAccountNumber(account.getAccountNumber(), page);

        Assert.assertTrue(transactionHistoryList.stream().anyMatch(x -> x.getDebit() == 20));
        Assert.assertTrue(transactionHistoryList.stream().anyMatch(x -> x.getDebit() == 10));
        Assert.assertTrue(transactionHistoryList.stream().anyMatch(x -> x.getDebit() == 30));

    }

    @Test
    public void WhenMakeMoreThan10TransactionsWillBeRecordedIn10LastestTransactions() throws Exception {
        Account account = new Account("Will Smith", "232432", 10000, "121321" );
        accountRepository.save(account);
        AccountService accountService = new AccountServiceImpl(accountRepository, transactionHistoryRepository);

        List<Integer> transactionAmounts = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<20; i++)
        {
            transactionAmounts.add(random.nextInt(20)*10);
        }

        transactionAmounts.forEach(x -> {
            try {
                accountService.deduct(account.getAccountNumber(), x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Pageable page = PageRequest.of(0, 10);
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAllByAccountNumber(account.getAccountNumber(), page);

        Assert.assertTrue(
            transactionAmounts.containsAll(
                    transactionHistoryList.stream().map(TransactionHistory::getDebit).collect(Collectors.toList()
                    ))
        );

    }


}
