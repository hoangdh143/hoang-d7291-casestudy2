package com.mitrais.view;

import lombok.Data;
import com.mitrais.model.TransferConfirmation;
import com.mitrais.viewhandler.Dispatcher;

import java.util.Scanner;

@Data
public class FundTransfer2 implements View {
    private Dispatcher dispatcher;

    public FundTransfer2() {
    }

    @Override
    public void display() {
        TransferConfirmation transferConfirmation = dispatcher.getTransferConfirmation();
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter transfer amount and \n" +
                "press enter to continue or \n" +
                "press enter to go back to Transaction: ");
        String input = in.nextLine();
        if (input.equals("")) {
            dispatcher.dispatch("TRANSACTION");
        } else {
            transferConfirmation.setTransferAmount(input);
            dispatcher.setTransferConfirmation(transferConfirmation);
            dispatcher.dispatch("FUNDTRANSFER3");
        };
    }
}
