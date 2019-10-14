package com.mitrais.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "account")
public class Account implements Comparable<Account> {
    @Id
    @Length(max = 6)
    private String accountNumber;

    @Length(max = 200)
    private String name;

    @Length(max = 6)
    private String pin;

    private Integer balance;


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
