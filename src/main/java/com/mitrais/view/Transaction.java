package com.mitrais.view;

import lombok.Data;
import com.mitrais.viewhandler.Dispatcher;

import java.util.Scanner;

@Data
public class Transaction implements View {
    private Dispatcher dispatcher;

    public Transaction() {
    }

    @Override
    public void display() {
        Scanner in = new Scanner(System.in);

        System.out.print("1. Withdraw\n" +
                "2. Fund Transfer\n" +
                "3. Your transaction history" +
                "4. Exit\n" +
                "Please choose option[3]:");
        String option = in.nextLine();
        switch (option) {
            case "1":
                dispatcher.dispatch("WITHDRAW");
                break;
            case "2":
                dispatcher.dispatch("FUNDTRANSFER");
                break;
            case "3":
                dispatcher.dispatch("TRANSHISTORY");
                break;
            case "4":
                dispatcher.dispatch("WELCOME");
                break;
            case "":
                dispatcher.dispatch("WELCOME");
                break;
            default:
                dispatcher.dispatch("TRANSACTION");
        }
    }
}
