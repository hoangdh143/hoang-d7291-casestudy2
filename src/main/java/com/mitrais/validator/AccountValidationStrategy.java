package com.mitrais.validator;

import com.mitrais.model.Account;

import java.util.Optional;

public enum AccountValidationStrategy implements ValidationStrategy {
    ACCOUNT_NUMBER(ValidationType.ACCOUNT_NUMBER) {
        @Override
        public <T extends Account> Optional<String> validate(T input) {
            try {
                Integer.parseInt(input.getAccountNumber());
            } catch (NumberFormatException e) {
                return Optional.of("Account Number should only contains numbers");
            }

            if (input.getAccountNumber().length() != 6) {
                return Optional.of("Account Number should have 6 digits length");
            }
            return Optional.empty();
        }
    },

    PIN(ValidationType.PIN) {
        @Override
        public <T extends Account> Optional<String> validate(T input) {
            try {
                Integer.parseInt(input.getPin());
            } catch (NumberFormatException e) {
                return Optional.of("PIN should only contains numbers");
            }

            if (input.getPin().length() != 6) {
                return Optional.of("PIN should have 6 digits length");
            }
            return Optional.empty();
        }
    },

    VALID_ACCOUNT(ValidationType.VALID_ACCOUNT) {
        @Override
        public <T extends Account> Optional<String> validate(T input) {
            return Optional.empty();
        }
    };

    private ValidationType validationType;

    private AccountValidationStrategy(ValidationType validationType) {
        this.validationType = validationType;
    }

    public ValidationType getValidationType() {
        return validationType;
    }

}
