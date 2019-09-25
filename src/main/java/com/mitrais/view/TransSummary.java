package com.mitrais.view;

import lombok.Data;
import com.mitrais.model.TransferSummary;
import com.mitrais.viewhandler.Dispatcher;

import java.util.Scanner;

@Data
public class TransSummary implements View {
    private Dispatcher dispatcher;

    public TransSummary() {
    }

    @Override
    public void display() {
        TransferSummary transferSummary = dispatcher.getTransferSummary();
        Scanner in = new Scanner(System.in);
        System.out.println(String.format("Fund Transfer Summary\n" +
                "Destination Account : %s\n" +
                "Transfer Amount     : $%s\n" +
                "Reference Number    : %s\n" +
                "Balance             : $%s\n" +
                "\n" +
                "1. Transaction\n" +
                "2. Exit\n" +
                "Choose option[2]:", transferSummary.getDestinationAccount(), transferSummary.getTransferAmount(),
                transferSummary.getReferenceNumber(), transferSummary.getBalance()));
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
