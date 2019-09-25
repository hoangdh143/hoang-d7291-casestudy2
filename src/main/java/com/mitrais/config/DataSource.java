package com.mitrais.config;

import com.mitrais.exception.DataSourceException;

import java.util.Map;

public interface DataSource<T> {
    Map<String, T> loadData() throws DataSourceException;
}
