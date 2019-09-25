package com.mitrais.view;

import lombok.Data;
import com.mitrais.model.TransferConfirmation;
import com.mitrais.viewhandler.Dispatcher;

import java.util.Scanner;

@Data
public class FundTransfer3 implements View {
    private Dispatcher dispatcher;
    private static String NUMERIC_STRING = "0123456789";

    public FundTransfer3() {
    }

    @Override
    public void display() {
        TransferConfirmation transferConfirmation = dispatcher.getTransferConfirmation();
        Scanner in = new Scanner(System.in);
        String refNumber = createRandomString();
        System.out.println(String.format("Reference Number: %s\n press enter to continue", refNumber));
        String nextStep = in.nextLine();
        transferConfirmation.setReferenceNumber(refNumber);
        dispatcher.setTransferConfirmation(transferConfirmation);
        dispatcher.dispatch("FUNDTRANSFER4");
    }


    private String createRandomString() {
        int count = 6;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
