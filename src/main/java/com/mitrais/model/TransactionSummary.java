package com.mitrais.model;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionSummary {
    private Date date;
    private Integer withDraw;
    private Integer balance;

    public TransactionSummary(Date date, Integer withDraw, Integer balance) {
        this.date = date;
        this.withDraw = withDraw;
        this.balance = balance;
    }
}
