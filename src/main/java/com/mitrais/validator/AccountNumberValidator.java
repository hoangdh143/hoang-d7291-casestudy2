package com.mitrais.validator;

import com.mitrais.model.Account;
import com.mitrais.repository.AccountRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AccountNumberValidator implements Validator {
    private Account account;
    private AccountRepository accountRepository;

    public AccountNumberValidator withAccount(Account account) {
        this.account = account;
        return this;
    }

    public AccountNumberValidator withAccountRepo(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        return this;
    }

    public AccountNumberValidator() {
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return String.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String accountNumber = (String) o;

        if (accountNumber.length() != 6) errors.reject("INVALID_ACCOUNT_NUMBER", "Account Number must have 6 characters");

        try {
            Integer.parseInt(accountNumber);
        } catch (NumberFormatException e) {
            errors.reject("INVALID_ACCOUNT_NUMBER", "Account Number should only contains numbers");
        }

        if (accountNumber.equals(account.getAccountNumber())) errors.reject("INVALID_ACCOUNT_NUMBER", "Account Number must not be the same as your Account Number");

        if (accountRepository != null && accountRepository.findByAccountNumber(accountNumber) == null) errors.reject("INVALID_ACCOUNT_NUMBER", "Account Number does not exist");
    }
}
