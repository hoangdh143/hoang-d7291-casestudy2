package com.mitrais.security;

import com.mitrais.model.Account;
import com.mitrais.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        final Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) throw new UsernameNotFoundException(accountNumber);
        return new CustomUserDetails(account);
     }
}