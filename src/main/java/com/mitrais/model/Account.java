package com.mitrais.model;

import lombok.Data;

@Data
public class Account implements Comparable<Account> {
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

    @Override
    public int compareTo(Account o) {
        if (name.equals(o.name) && pin.equals(o.pin) && balance.equals(o.balance) && accountNumber.equals(o.accountNumber))
        return 0;
        return balance - o.balance;
    }
}
