package com.mitrais.view;

import com.mitrais.model.Account;
import com.mitrais.model.TransactionHistory;
import com.mitrais.model.TransferSummary;
import com.mitrais.repository.TransactionHistoryRepoFactory;
import com.mitrais.repository.TransactionHistoryRepository;
import com.mitrais.viewhandler.Dispatcher;
import lombok.Data;

import java.util.Scanner;
import java.util.stream.Collectors;

@Data
public class TransHistory implements View {
    private static final int PAGE = 0;
    private static final int LIMIT = 10;
    private Dispatcher dispatcher;
    private TransactionHistoryRepository transactionHistoryRepository = TransactionHistoryRepoFactory.getTransactionHistoryRepository();

    public TransHistory() {
    }

    @Override
    public void display() {
        Account account = dispatcher.getAccount();
        Scanner in = new Scanner(System.in);
        String transactionHistory = transactionHistoryRepository.findByAccountNumber(account.getAccountNumber(), PAGE, LIMIT).stream()
                .map(TransactionHistory::toString).collect(Collectors.joining("\n"));

        System.out.println(String.format("Transaction History\n" +
                "Account Number : %s\n" +
                "Transaction History: \n" +
                "%s\n" +
                "1. Transaction\n" +
                "2. Exit\n" +
                "Choose option[2]:", account.getAccountNumber(), transactionHistory));
        String option = in.nextLine();
        switch (option) {
            case "1":
                dispatcher.dispatch("TRANSACTION");
                break;
            case "2":
                dispatcher.dispatch("WELCOME");
                break;
            default:
                dispatcher.dispatch("WELCOME");
        }
    }
}
