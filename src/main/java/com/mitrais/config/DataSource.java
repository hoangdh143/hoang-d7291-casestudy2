package com.mitrais.config;

import com.mitrais.exception.DataSourceException;
import com.mitrais.model.Account;
import com.mitrais.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface DataSource<T> {
    Map<String, T> loadData() throws DataSourceException;

    void saveToRepo(JpaRepository<Account, String> accountRepository) throws DataSourceException;
}
