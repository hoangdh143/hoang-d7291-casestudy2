package com.mitrais.validator;

import com.mitrais.model.Account;

import java.util.Optional;

public interface ValidationStrategy {
    <T extends Account> Optional<String> validate(T input);
    ValidationType getValidationType();
}