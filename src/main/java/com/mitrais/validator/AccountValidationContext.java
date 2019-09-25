package com.mitrais.validator;

import com.mitrais.model.Account;

import java.util.*;

public class AccountValidationContext {
    private Set<ValidationStrategy> accountValidationStrategies;

    public AccountValidationContext() {
        this.accountValidationStrategies = new LinkedHashSet<>();
    }

    public AccountValidationContext(Set<ValidationStrategy> accountValidationStrategies) {
        this.accountValidationStrategies = accountValidationStrategies;
    }

    public void addStrategy(ValidationStrategy strategy) {
        this.accountValidationStrategies.add(strategy);
    }
    /*
     * This method performs validation for fields one by one and return the invalid one if found.
     * Otherwise, it will continue validating remaining fields. If all the fields are valid then will return null.
     */
    public String execute(Account account) {
        ValidationStrategy accountValidation;
        for (ValidationStrategy accountValidationStrategy : accountValidationStrategies) {
            accountValidation = accountValidationStrategy;
            Optional<String> errorCode = accountValidation.validate(account);
            if (errorCode.isPresent()) {
                return errorCode.get();
            }
        }
        return null;
    }
    /*
     * This method performs validation for fields one by one and
     * add the invalid one into a list if found and returns that list.
     */
    public List<String> executeAndGetList(Account account) {
        ValidationStrategy accountValidation;
        List<String> errorCodes = new ArrayList<>();
        for (ValidationStrategy accountValidationStrategy : accountValidationStrategies) {
            accountValidation = accountValidationStrategy;
            Optional<String> errorCode = accountValidation.validate(account);
            errorCode.ifPresent(errorCodes::add);
        }
        return errorCodes;
    }
}
