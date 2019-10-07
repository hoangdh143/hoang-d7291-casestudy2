package com.mitrais.viewhandler;

import com.mitrais.view.*;

public enum ViewName {
    WELCOME(new Welcome()),
    TRANSACTION(new Transaction()),
    TRANSHISTORY(new TransHistory()),
    WITHDRAW(new Withdraw()),
    OTHERWITHDRAW(new OtherWithdraw()),
    SUMMARY(new Summary()),
    FUNDTRANSFER(new FundTransfer()),
    FUNDTRANSFER2(new FundTransfer2()),
    FUNDTRANSFER3(new FundTransfer3()),
    FUNDTRANSFER4(new FundTransfer4()),
    TRANSSUMMARY(new TransSummary()),
    FILELOADER(new FileLoader());

    private View view;

    ViewName(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
