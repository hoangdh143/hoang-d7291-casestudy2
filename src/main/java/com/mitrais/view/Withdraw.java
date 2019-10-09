package com.mitrais.view;

import lombok.Data;
import com.mitrais.model.Account;
import com.mitrais.model.TransactionSummary;
import com.mitrais.repository.AccountRepoFactory;
import com.mitrais.repository.AccountRepositoryDeprecated;
import com.mitrais.viewhandler.Dispatcher;

import java.util.Scanner;

@Data
public class Withdraw implements View {
    private Dispatcher dispatcher;
    private AccountRepositoryDeprecated accountRepositoryDeprecated = AccountRepoFactory.getAccountRepositoryDeprecated();

    public Withdraw() {
    }

    @Override
    public void display() {
        Account account = dispatcher.getAccount();
        Scanner in = new Scanner(System.in);
        System.out.print("1. $10\n" +
                "2. $50\n" +
                "3. $100\n" +
                "4. Other\n" +
                "5. Back\n" +
                "Please choose option[5]:");
        String option = in.nextLine();
        try {
            TransactionSummary transactionSummary;
            switch (option) {
                case "1":
                    transactionSummary = accountRepositoryDeprecated.deduct(account, 10);
                    dispatcher.setTransactionSummary(transactionSummary);
                    dispatcher.dispatch("SUMMARY");
                    break;
                case "2":
                    transactionSummary = accountRepositoryDeprecated.deduct(account, 50);
                    dispatcher.setTransactionSummary(transactionSummary);
                    dispatcher.dispatch("SUMMARY");
                    break;
                case "3":
                    transactionSummary = accountRepositoryDeprecated.deduct(account, 100);
                    dispatcher.setTransactionSummary(transactionSummary);
                    dispatcher.dispatch("SUMMARY");
                    break;
                case "4":
                    dispatcher.dispatch("OTHERWITHDRAW");
                    break;
                case "5":
                    dispatcher.dispatch("TRANSACTION");
                    break;
                default:
                    dispatcher.dispatch("TRANSACTION");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.display();
        }
    }
}
