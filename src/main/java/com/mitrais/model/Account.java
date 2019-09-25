package com.mitrais.model;

import lombok.Data;

@Data
public class Account {
    private String name;
    private String pin;
    private Integer balance;
    private String accountNumber;

    public Account(String name, String pin, Integer balance, String accountNumber) {
        setName(name);
        setPin(pin);
        setBalance(balance);
        setAccountNumber(accountNumber);
    }

    public Account() {
    }
}
