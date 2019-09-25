package com.mitrais.view;

import lombok.Data;
import com.mitrais.model.TransactionSummary;
import com.mitrais.viewhandler.Dispatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@Data
public class Summary implements View {
    private Dispatcher dispatcher;

    public Summary() {
    }

    @Override
    public void display() {
        TransactionSummary transactionSummary = dispatcher.getTransactionSummary();

        Scanner in = new Scanner(System.in);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm a");
        String summary = String.format("Summary\n" +
                "Date : %s\n" +
                "Withdraw : $%d\n" +
                "Balance : $%d\n" +
                "\n" +
                "1. Transaction \n" +
                "2. Exit\n" +
                "Choose option[2]:",
                dateFormat.format(transactionSummary.getDate()),
                transactionSummary.getWithDraw(),
                transactionSummary.getBalance());
        System.out.println(summary);
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
                break;
        }
    }
}
