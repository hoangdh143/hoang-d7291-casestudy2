package com.mitrais.model;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TransactionSummary {
    private LocalDateTime date;
    private Integer withDraw;
    private Integer balance;

    public TransactionSummary(LocalDateTime date, Integer withDraw, Integer balance) {
        this.date = date;
        this.withDraw = withDraw;
        this.balance = balance;
    }
}
