package com.mitrais.view;

import lombok.Data;
import com.mitrais.model.Account;
import com.mitrais.model.TransferConfirmation;
import com.mitrais.viewhandler.Dispatcher;

import java.util.Scanner;

@Data
public class FundTransfer implements View {
    private Dispatcher dispatcher;

    public FundTransfer() {
    }

    @Override
    public void display() {
        Account account = dispatcher.getAccount();
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter destination account and press enter to continue or \n" +
                "press enter to go back to Transaction: ");
        String accountNumber = in.nextLine();
        if (accountNumber.equals("")) {
            dispatcher.dispatch("TRANSACTION");
        } else {
            TransferConfirmation transferConfirmation = new TransferConfirmation(account, accountNumber, "");
            dispatcher.setTransferConfirmation(transferConfirmation);
            dispatcher.dispatch("FUNDTRANSFER2");
        };
    }
}
