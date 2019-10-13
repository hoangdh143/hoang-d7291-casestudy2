package com.mitrais.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AccountNumberValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return String.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String accountNumber = (String) o;
        if (accountNumber.length() != 6) errors.reject("INVALID_ACCOUNT_NUMBER", "Account length must have 6 characters");
        try {
            Integer.parseInt(accountNumber);
        } catch (NumberFormatException e) {
            errors.rejectValue("INVALID_ACCOUNT_NUMBER", "Account Number should only contains numbers");
        }

    }
}
