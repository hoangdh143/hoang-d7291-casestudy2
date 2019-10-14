package com.mitrais.validator;

import com.mitrais.model.Account;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransferAmountValidator implements Validator {
    private Account account;

    public TransferAmountValidator withAccount(Account account) {
        this.account = account;
        return this;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Integer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Integer amount = (Integer) o;
        if (amount < 0) errors.rejectValue("INVALID_AMOUNT", "Minimum amount to withdraw is $1");
        if (amount > 1000) errors.rejectValue("INVALID_AMOUNT", "Maximum amount to withdraw is $1000");
        if (account != null && amount > account.getBalance()) errors.rejectValue("INVALID_AMOUNT", "Insufficient balance $" + amount);
    }
}
