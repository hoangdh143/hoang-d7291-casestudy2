package com.mitrais.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransferAmountValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Integer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Integer amount = (Integer) o;
        if (amount < 0) errors.rejectValue("INVALID_AMOUNT", "The amount must be greater than 0");
    }
}
