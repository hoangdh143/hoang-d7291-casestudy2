package com.mitrais.config;

import com.mitrais.exception.DataSourceException;
import com.mitrais.model.Account;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileDataSource implements DataSource<Account> {
    static final String fileUrl = "Accounts.csv";

    @Override
    public Map<String, Account> loadData() throws DataSourceException {
        Map<String, Account> map = new HashMap<>();
        File inputF = new File(fileUrl);
        try {
            InputStream inputStream = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            map = br.lines().map(this::mapToAccount).collect(Collectors.toMap(Account::getAccountNumber, Function.identity()));

            if (map.size() < 20) throw new DataSourceException("File should not have less than 20 rows");

        } catch (FileNotFoundException e) {
            throw new DataSourceException("File not found");
        } catch (IllegalStateException e) {
            throw new DataSourceException("Duplicated key values");
        } catch (NumberFormatException e) {
            throw new DataSourceException("Could not read number from file");
        }
        return map;
    }

    private Account mapToAccount(String line) {
        String[] p = line.split(",");// a CSV has comma separated lines
        Account account = new Account();
        account.setName(p[0]);//<-- this is the first column in the csv file
        account.setPin(p[1]);
        account.setBalance(Integer.parseInt(p[2].trim()));
        account.setAccountNumber(p[3]);
        return account;
    }
}
