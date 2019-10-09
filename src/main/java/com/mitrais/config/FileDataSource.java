package com.mitrais.config;

import com.mitrais.exception.DataSourceException;
import com.mitrais.model.Account;
import com.mitrais.validator.AccountValidationContext;
import com.mitrais.validator.AccountValidationStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class FileDataSource implements DataSource<Account> {
    private String fileUrl;
    private static final int MAX_ROW = 20;

    private AccountValidationContext context;

    public FileDataSource(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public Map<String, Account> loadData() throws DataSourceException {

        Map<String, Account> map = new HashMap<>();

        context = new AccountValidationContext();
        context.addStrategy(AccountValidationStrategy.ACCOUNT_NUMBER);
        context.addStrategy(AccountValidationStrategy.PIN);

        try {
            File file = new File(fileUrl);
            InputStream inputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            Stream<Account> accountStream = br.lines().map(this::mapToAccount).filter(Objects::nonNull);
            streamToMapWithValidation(accountStream, map);

            if (map.size() < MAX_ROW)
                throw new DataSourceException("File should not have less than " + MAX_ROW + " rows");

        } catch (FileNotFoundException e) {
            throw new DataSourceException("File not found");
        }

        System.out.println("File loaded! \n");
        return map;
    }

    @Override
    public void saveToRepo(JpaRepository<Account, String> accountRepository) throws DataSourceException{
        accountRepository.saveAll(loadData().values());
    }

    private void streamToMapWithValidation(Stream<Account> accountStream, Map<String, Account> map) {
        accountStream.forEach(x -> {
            Account accountExists = map.get(x.getAccountNumber());
            if (accountExists != null) {
                if (accountExists.equals(x)) {
                    System.out.println("There can't be duplicated records " + x);
                } else {
                    System.out.println("There can't be 2 different accounts with the same Account Number " + x.getAccountNumber());
                }
            } else {
                map.put(x.getAccountNumber(), x);
            }
        });
    }

    private Account mapToAccount(String line) {
        String[] p = line.split(",");
        if (p.length != 4) {
            System.out.println("Could not read Account info at line: " + line);
            return null;
        }

        Account account = new Account();
        account.setName(p[0].trim());
        account.setPin(p[1].trim());
        account.setAccountNumber(p[3].trim());

        try {
            account.setBalance(Integer.parseInt(p[2].trim()));
        } catch (NumberFormatException e) {
            System.out.println("Account number " + account.getAccountNumber() + " has error: Balance should only contains numbers");
            return null;
        }

        String errorCode = context.execute(account);
        if (errorCode != null) {
            System.out.println("Account number " + account.getAccountNumber() + " has error: " + errorCode);
            return null;
        }

        return account;
    }
}
