package com.mitrais.config;

import com.mitrais.exception.DataSourceException;
import com.mitrais.model.Account;
import com.mitrais.validator.AccountValidationContext;
import com.mitrais.validator.AccountValidationStrategy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileDataSource implements DataSource<Account> {
    private static final String FILE_URL = "Accounts.csv";
    private static final int MAX_ROW = 20;

    private AccountValidationContext context;

    @Override
    public Map<String, Account> loadData() throws DataSourceException {

        Map<String, Account> map = new HashMap<>();
        File inputF = new File(FILE_URL);

        context = new AccountValidationContext();
        context.addStrategy(AccountValidationStrategy.ACCOUNT_NUMBER);
        context.addStrategy(AccountValidationStrategy.PIN);

        try {
            InputStream inputStream = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            Stream<Account> accountStream = br.lines().map(this::mapToAccount);
            streamToMapWithValidation(accountStream, map);

            if (map.size() < MAX_ROW)
                throw new DataSourceException("File should not have less than " + MAX_ROW + " rows");

        } catch (FileNotFoundException e) {
            throw new DataSourceException("File not found");
        } catch (NumberFormatException e) {
            throw new DataSourceException("Could not read number from file");
        } catch (RuntimeException e) {
            throw new DataSourceException(e.getMessage());
        }

        return map;
    }

    private void streamToMapWithValidation(Stream<Account> accountStream, Map<String, Account> map) {
        accountStream.forEach(x -> {
            Account accountExists = map.get(x.getAccountNumber());
            if (accountExists != null) {
                if (accountExists.equals(x)) throw new RuntimeException("There can't be duplicated records " + x);
                throw new RuntimeException("There can't be 2 different accounts with the same Account Number " + x.getAccountNumber());
            }
            map.put(x.getAccountNumber(), x);
        });
    }

    private Account mapToAccount(String line) throws RuntimeException {
        String[] p = line.split(",");
        if (p.length != 4) throw new RuntimeException("Could not read Account info at line: " + line);

        Account account = new Account();
        account.setName(p[0].trim());
        account.setPin(p[1].trim());
        account.setBalance(Integer.parseInt(p[2].trim()));
        account.setAccountNumber(p[3].trim());

        String errorCode = context.execute(account);
        if (errorCode != null)
            throw new RuntimeException("Account number " + account.getAccountNumber() + " has error: " + errorCode);

        return account;
    }
}
