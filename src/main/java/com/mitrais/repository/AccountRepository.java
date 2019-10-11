package com.mitrais.repository;

import com.mitrais.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByAccountNumberAndPin(String accountNumber, String pin);

    Account findByAccountNumber(String accountNumber);
}
