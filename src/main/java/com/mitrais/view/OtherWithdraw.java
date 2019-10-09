package com.mitrais.view;

import lombok.Data;
import com.mitrais.model.Account;
import com.mitrais.model.TransactionSummary;
import com.mitrais.repository.AccountRepoFactory;
import com.mitrais.repository.AccountRepositoryDeprecated;
import com.mitrais.viewhandler.Dispatcher;

import java.util.Scanner;

@Data
public class OtherWithdraw implements View {
    private Dispatcher dispatcher;
    private AccountRepositoryDeprecated accountRepositoryDeprecated = AccountRepoFactory.getAccountRepositoryDeprecated();

    @Override
    public void display() {
        Account account = dispatcher.getAccount();
        Scanner in = new Scanner(System.in);
        System.out.println("Other Withdraw\n" +
                "Enter amount to withdraw:");
        String input = in.nextLine();
        try {
            int amount = Integer.parseInt(input);
            TransactionSummary transactionSummary;
            transactionSummary = accountRepositoryDeprecated.deduct(account, amount);
            dispatcher.setTransactionSummary(transactionSummary);
            dispatcher.dispatch("SUMMARY");
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
            this.display();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.display();
        }
    }
}
