package com.mitrais.view;

import lombok.Data;
import com.mitrais.model.TransferConfirmation;
import com.mitrais.model.TransferSummary;
import com.mitrais.repository.AccountRepoFactory;
import com.mitrais.viewhandler.Dispatcher;

import java.util.Scanner;

@Data
public class FundTransfer4 implements View {
    private Dispatcher dispatcher;

    public FundTransfer4() {
    }

    @Override
    public void display() {
        TransferConfirmation transferConfirmation = dispatcher.getTransferConfirmation();
        Scanner in = new Scanner(System.in);
        System.out.println(String.format("Transfer Confirmation\n" +
                "Destination Account : %s\n" +
                "Transfer Amount     : $%s\n" +
                "Reference Number    : %s\n" +
                "\n" +
                "1. Confirm Trx\n" +
                "2. Cancel Trx\n" +
                "Choose option[2]:",
                transferConfirmation.getDestinationAccount(),
                transferConfirmation.getTransferAmount(),
                transferConfirmation.getReferenceNumber()));
        String confirm = in.nextLine();
        switch (confirm) {
            case "1":
                try {
                TransferSummary transferSummary = AccountRepoFactory.getAccountRepositoryDeprecated().transfer(
                        transferConfirmation.getAccount().getAccountNumber(),
                        transferConfirmation.getDestinationAccount(),
                        transferConfirmation.getTransferAmount(),
                        transferConfirmation.getReferenceNumber()
                );
                dispatcher.setTransferSummary(transferSummary);
                dispatcher.dispatch("TRANSSUMMARY");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    dispatcher.dispatch("TRANSACTION");
                }
                break;
            case "2":
                dispatcher.dispatch("TRANSACTION");
                break;
            default:
                dispatcher.dispatch("TRANSACTION");
        }
    }
}
