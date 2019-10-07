package com.mitrais.view;

import com.mitrais.config.FileDataSource;
import com.mitrais.repository.*;
import com.mitrais.viewhandler.Dispatcher;
import lombok.Data;

import java.io.FileNotFoundException;
import java.util.Scanner;

@Data
public class FileLoader implements View {
    private Dispatcher dispatcher;

    @Override
    public void display() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter file path to import accounts: ");
        String filePath = in.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("File path empty, please try again");
            this.display();
        }

        try {
            FileDataSource fileDataSource = new FileDataSource(filePath);
            TransactionHistoryRepoFactory.setTransactionHistoryRepository(new TransactionHistoryRepositoryImpl());
            AccountRepository accountRepository = new AccountRepositoryImpl(fileDataSource, TransactionHistoryRepoFactory.getTransactionHistoryRepository());
            AccountRepoFactory.setAccountRepository(accountRepository);

        } catch (FileNotFoundException e) {
            System.out.println("File not found, please try again");
            this.display();
        }

    }
}
