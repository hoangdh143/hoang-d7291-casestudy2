package com.mitrais.view;

import com.mitrais.config.FileDataSource;
import com.mitrais.exception.DataSourceException;
import com.mitrais.repository.*;
import com.mitrais.viewhandler.Dispatcher;
import lombok.Data;

import java.util.Scanner;

@Data
public class FileLoader implements View {
    private Dispatcher dispatcher;

    @Override
    public void display() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter file path to import accounts [Accounts.csv]: ");
        String filePath = in.nextLine().trim();

        if (filePath.isEmpty()) {
            filePath = "Accounts.csv";
        }

        try {
            FileDataSource fileDataSource = new FileDataSource(filePath);
            TransactionHistoryRepoFactory.setTransactionHistoryRepositoryDeprecated(new TransactionHistoryRepositoryDeprecatedImpl());
            AccountRepositoryDeprecated accountRepositoryDeprecated = new AccountRepositoryDeprecatedImpl(fileDataSource, TransactionHistoryRepoFactory.getTransactionHistoryRepositoryDeprecated());
            AccountRepoFactory.setAccountRepositoryDeprecated(accountRepositoryDeprecated);

        } catch (DataSourceException e) {
            System.out.println(e.getMessage());
            this.display();
        }

    }
}
