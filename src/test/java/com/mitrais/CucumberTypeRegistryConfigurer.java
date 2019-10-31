package com.mitrais;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class CucumberTypeRegistryConfigurer implements TypeRegistryConfigurer {
    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
            .toFormatter();

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineParameterType(new ParameterType<>(
                "localDate",
                "((.*) \\d{1,2}, \\d{4})",
                LocalDate.class,
                (String s) -> LocalDate.parse(s, formatter)
        ));
    }
}
